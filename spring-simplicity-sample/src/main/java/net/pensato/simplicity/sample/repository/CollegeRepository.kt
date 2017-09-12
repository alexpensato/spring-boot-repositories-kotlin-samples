package net.pensato.simplicity.sample.repository

import net.pensato.simplicity.sample.domain.College
import net.pensato.simplicity.jdbc.AbstractJdbcRepository
import net.pensato.simplicity.jdbc.JdbcRepository
import net.pensato.simplicity.jdbc.mapper.TransactionalRowMapper
import net.pensato.simplicity.sample.mapper.CollegeMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.LinkedHashMap

interface CollegeRepository: JdbcRepository<College, Long> {}

@Repository(value = "collegeRepository")
open class CollegeRepositoryImpl(@Autowired jdbcTemplate: JdbcTemplate) : CollegeRepository,
        AbstractJdbcRepository<College, Long>(jdbcTemplate, "college", College::class.java, "id")
{
    override val rowMapper: TransactionalRowMapper<College>
        get() = CollegeMapper

}
