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
package net.pensato.simplicity.sample.controller

import net.pensato.simplicity.sample.domain.College
import net.pensato.simplicity.sample.domain.Student
import net.pensato.simplicity.sample.repository.CollegeRepository
import net.pensato.simplicity.sample.repository.StudentRepository
import net.pensato.simplicity.web.AbstractController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/university")
class UniversityController
@Autowired constructor(val collegeRepository: CollegeRepository, val studentRepository: StudentRepository)
    : AbstractController<College, Long, CollegeRepository>(collegeRepository){

    @RequestMapping(value = "/{collegeId}/students", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findAllStudentsByCollege(@PathVariable collegeId: Long?): List<Student> {
        Assert.notNull(collegeId, "You must provide an ID to locate nested itens in an auxiliary repository.")
        return studentRepository.findAllByCollege(collegeId!!)
    }


}
