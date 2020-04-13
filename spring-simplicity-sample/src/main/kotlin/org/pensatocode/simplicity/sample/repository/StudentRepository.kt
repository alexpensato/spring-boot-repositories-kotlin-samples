/*
 * Copyright 2017-2020 twitter.com/PensatoAlex
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pensatocode.simplicity.sample.repository

import org.pensatocode.simplicity.sample.domain.Student
import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository
import org.pensatocode.simplicity.jdbc.JdbcRepository
import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper
import org.pensatocode.simplicity.sample.mapper.StudentMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types

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
        val queryString = sqlGenerator.selectAll(tableDesc, "college_id = ?")
        return jdbcTemplate.query(
                queryString,
                arrayOf(collegeId),
                intArrayOf(Types.INTEGER),
                rowMapper)
    }

}
