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
package org.pensatocode.simplicity.sample.repository

import org.pensatocode.simplicity.sample.domain.College
import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository
import org.pensatocode.simplicity.jdbc.JdbcRepository
import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper
import org.pensatocode.simplicity.sample.mapper.CollegeMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

interface CollegeRepository: JdbcRepository<College, Long> {}

@Repository(value = "collegeRepository")
open class CollegeRepositoryImpl(@Autowired jdbcTemplate: JdbcTemplate) : CollegeRepository,
        AbstractJdbcRepository<College, Long>(jdbcTemplate, "college", College::class.java, "id")
{
    override val rowMapper: TransactionalRowMapper<College>
        get() = CollegeMapper

}
