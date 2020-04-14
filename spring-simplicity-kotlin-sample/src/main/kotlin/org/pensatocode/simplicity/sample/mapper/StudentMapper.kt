/*
 * Copyright 2017-2020 Alex Magalhaes <alex@pensatocode.org>
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
package org.pensatocode.simplicity.sample.mapper

import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper
import org.pensatocode.simplicity.sample.domain.Student
import java.sql.ResultSet
import java.util.LinkedHashMap

object StudentMapper: TransactionalRowMapper<Student> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Student {
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
