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

import net.pensato.data.jdbc.sample.domain.College
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.*
import org.springframework.jdbc.core.*

@Repository
open class CollegeRepository @Autowired constructor(var jdbcTemplate: JdbcTemplate)
{
    @Transactional(readOnly=true)
    open fun findAll(): List<College> {
        return jdbcTemplate.query("select * from college", CollegeRowMapper)
    }

    @Transactional(readOnly=true)
    open fun findOne(id: Long): College {
        val resultList = jdbcTemplate.query("select * from college where id = ?", arrayOf(id), CollegeRowMapper)
        Assert.notEmpty(resultList, "")
        return resultList[0]
    }

    @Transactional
    open fun delete(id: Long): Int {
        return jdbcTemplate.update("delete from college where id = ?", id)
    }

    @Transactional
    open fun update(c: College): Int {
        return jdbcTemplate.update(
                "update college set name = ?, city = ? where id = ?",
                arrayOf(c.name, c.city, c.id),
                intArrayOf(Types.VARCHAR, Types.VARCHAR, Types.INTEGER))
    }

    @Transactional
    open fun create(c: College): Long {
        val sql = "insert into college (name, city) values(?,?)"
        val factory = PreparedStatementCreatorFactory(sql, Types.VARCHAR, Types.VARCHAR)
        factory.setReturnGeneratedKeys(true)
        val params = listOf(c.name, c.city)
        val psc: PreparedStatementCreator = factory.newPreparedStatementCreator(params)
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(psc, keyHolder)
        return (keyHolder.keys.get("id") as Int).toLong()
    }

    companion object CollegeRowMapper: RowMapper<College>
    {
        override fun mapRow(rs: ResultSet, rowNum: Int): College
        {
            val entity = College()
            entity.id = rs.getLong("id")
            entity.name = rs.getString("name")
            entity.city = rs.getString("city")

            return entity
        }
    }
}

