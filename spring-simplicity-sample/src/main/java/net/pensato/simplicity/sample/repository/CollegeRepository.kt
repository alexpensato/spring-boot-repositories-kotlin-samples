package net.pensato.simplicity.sample.repository

import net.pensato.simplicity.sample.domain.College
import net.pensato.simplicity.jdbc.AbstractJdbcRepository
import net.pensato.simplicity.jdbc.JdbcRepository
import net.pensato.simplicity.jdbc.mapper.TransactionalRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.LinkedHashMap

interface CollegeRepository: JdbcRepository<College, Long> {}

@Repository(value = "collegeRepository")
open class CollegeRepositoryImpl : AbstractJdbcRepository<College, Long>, CollegeRepository
{
    @Autowired constructor(jdbcTemplate: JdbcTemplate): super(jdbcTemplate, "college", College::class, "id") {}

    override val rowMapper: TransactionalRowMapper<College>
        get() = Companion


    companion object Companion: TransactionalRowMapper<College> {

        override fun mapRow(rs: ResultSet, rowNum: Int): College
        {
            val entity = College()
            entity.id = rs.getLong("id")
            entity.name = rs.getString("name")
            entity.city = rs.getString("city")

            return entity
        }

        override fun mapColumns(entity: College): Map<String, Any> {
            val mapping = LinkedHashMap<String, Any>()
            mapping.put("id", entity.id)
            mapping.put("name", entity.name)
            mapping.put("city", entity.city)
            return mapping
        }
    }
}
