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
package org.pensatocode.simplicity.sample.controller

import org.pensatocode.simplicity.sample.domain.College
import org.pensatocode.simplicity.sample.domain.Student
import org.pensatocode.simplicity.sample.repository.CollegeRepository
import org.pensatocode.simplicity.sample.repository.StudentRepository
import org.pensatocode.simplicity.web.AbstractController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/university")
class UniversityController
@Autowired constructor(collegeRepository: CollegeRepository, val studentRepository: StudentRepository)
    : AbstractController<College, Long>(collegeRepository){

    @GetMapping("/{collegeId}/students")
    @ResponseBody
    fun findAllStudentsByCollege(@PathVariable collegeId: Long?): List<Student> {
        Assert.notNull(collegeId, "You must provide an ID to locate nested itens in an auxiliary repository.")
        return studentRepository.findAllByCollege(collegeId!!)
    }


}
