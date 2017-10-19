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
package net.pensato.data.cassandra.sample.repository

import net.pensato.data.cassandra.sample.domain.Student
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.Instant

interface StudentRepository : CrudRepository<Student, String> {

    @Query(value="SELECT * FROM student WHERE college=?0")
    fun findByCollege(college: String): List<Student>;

    @Query("SELECT * FROM student WHERE enrollment > ?0 ALLOW FILTERING")
    fun findStudentHasEnrollmentGreaterThan(enrollment: Instant): List<Student>;

}

