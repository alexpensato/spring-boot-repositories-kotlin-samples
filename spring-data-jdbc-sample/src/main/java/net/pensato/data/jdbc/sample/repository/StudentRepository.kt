/*
 * Copyright 2017 twitter.com/PensatoAlex
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
package net.pensato.data.jdbc.sample.repository

import net.pensato.data.jdbc.sample.domain.Student
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.Types

@Repository
open class StudentRepository @Autowired constructor(var jdbcTemplate: JdbcTemplate)
{
    @Transactional(readOnly=true)
    open fun findAllByCollege(collegeId: Long): List<Student> {
        return jdbcTemplate.query(
                "select * from student where college_id = ?",
                arrayOf(collegeId),
                intArrayOf(Types.INTEGER),
                StudentRowMapper)
    }

    companion object StudentRowMapper: RowMapper<Student>
    {
        override fun mapRow(rs: ResultSet, rowNum: Int): Student
        {
            val entity = Student()
            entity.id = rs.getLong("id")
            entity.name = rs.getString("name")
            entity.address = rs.getString("address")

            return entity
        }
    }
}


