package net.pensato.simplicity.sample.repository

import net.pensato.simplicity.sample.domain.Student
import net.pensato.simplicity.jdbc.AbstractJdbcRepository
import net.pensato.simplicity.jdbc.JdbcRepository
import net.pensato.simplicity.jdbc.mapper.TransactionalRowMapper
import net.pensato.simplicity.sample.mapper.StudentMapper
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
    AbstractJdbcRepository<Student, Long>(jdbcTemplate, "student", Student::class.java, "id")
{
    override val rowMapper: TransactionalRowMapper<Student>
        get() = StudentMapper

    @Transactional(readOnly=true)
    override fun findAllByCollege(collegeId: Long): List<Student> {
        return jdbcTemplate.query(
                "select * from student where college_id = ?",
                arrayOf(collegeId),
                intArrayOf(Types.INTEGER),
                rowMapper)
    }

}
