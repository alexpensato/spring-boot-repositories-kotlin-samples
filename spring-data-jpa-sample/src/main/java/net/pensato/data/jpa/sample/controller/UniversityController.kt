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
package net.pensato.data.jpa.sample.controller

import net.pensato.data.jpa.sample.domain.College
import net.pensato.data.jpa.sample.domain.Student
import net.pensato.data.jpa.sample.repository.CollegeRepository
import net.pensato.data.jpa.sample.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/university")
class UniversityController @Autowired constructor(val collegeRepository: CollegeRepository, val studentRepository: StudentRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findAll(): List<College> {
        return collegeRepository.findAll().toList()
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findById(@PathVariable id: Long?): College {
        Assert.notNull(id, "You must provide an ID to locate an item in the repository.")
        return collegeRepository.findOne(id!!)
    }

    @RequestMapping(value = "/{collegeId}/students", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findAllStudentsByCollege(@PathVariable collegeId: Long?): List<Student> {
        Assert.notNull(collegeId, "You must provide an ID to locate nested itens in an auxiliary repository.")
        val college = collegeRepository.findOne(collegeId!!)
        Assert.notNull(college, "ID provided does not correspond to a valid College Id.")
        return studentRepository.findAllByCollege(college)
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.DELETE))
    @ResponseBody
    fun delete(@PathVariable id: Long?): String {
        Assert.notNull(id, "You must provide an ID to delete an item from the repository.")
        collegeRepository.delete(id!!)
        return "Item corresponding to ID $id has been deleted."
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.PUT))
    @ResponseBody
    fun update(@PathVariable id: Long?, @RequestBody t: College): String {
        Assert.notNull(id, "You must provide an ID to update an item in the repository.")
        Assert.state(t.id == id, "The item you are trying to update does not exist in the repository.")
        t.id = id!!
        return "Item updated: ${collegeRepository.save(t)}."
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody t: College): College {
        val c = collegeRepository.save(t)
        return c
    }

}
