package net.pensato.simplicity.sample.repository

import net.pensato.simplicity.sample.domain.Student
import net.pensato.simplicity.jdbc.AbstractJdbcRepository
import net.pensato.simplicity.jdbc.JdbcRepository
import net.pensato.simplicity.jdbc.mapper.TransactionalRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.Types
import java.util.LinkedHashMap

interface StudentRepository: JdbcRepository<Student, Long> {
    fun findAllByCollege(collegeId: Long): List<Student>
}

@Repository(value = "studentRepository")
open class StudentRepositoryImpl(@Autowired jdbcTemplate: JdbcTemplate) : StudentRepository,
    AbstractJdbcRepository<Student, Long>(jdbcTemplate, "student", Student::class, "id")
{
    override val rowMapper: TransactionalRowMapper<Student>
        get() = Companion

    @Transactional(readOnly=true)
    override fun findAllByCollege(collegeId: Long): List<Student> {
        return jdbcTemplate.query(
                "select * from student where college_id = ?",
                arrayOf(collegeId),
                intArrayOf(Types.INTEGER),
                rowMapper)
    }

    companion object Companion: TransactionalRowMapper<Student> {

        override fun mapRow(rs: ResultSet, rowNum: Int): Student
        {
            val entity = Student()
            entity.id = rs.getLong("id")
            entity.name = rs.getString("name")
            entity.address = rs.getString("address")

            return entity
        }

        override fun mapColumns(entity: Student): Map<String, Any> {
            val mapping = LinkedHashMap<String, Any>()
            mapping.put("id", entity.id)
            mapping.put("name", entity.name)
            mapping.put("address", entity.address)
            return mapping
        }
    }
}
