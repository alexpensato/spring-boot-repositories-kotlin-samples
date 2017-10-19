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
package net.pensato.data.cassandra.sample.controller

import net.pensato.data.cassandra.sample.domain.College
import net.pensato.data.cassandra.sample.domain.Student
import net.pensato.data.cassandra.sample.repository.CollegeRepository
import net.pensato.data.cassandra.sample.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/university")
class UniversityController @Autowired constructor(val collegeRepository: CollegeRepository, val studentRepository: StudentRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun findAll(pageable: Pageable): List<College> {
        return collegeRepository.findAll().toList()
    }

    @RequestMapping(value = "/{city}", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findByCity(@PathVariable city: String?): College {
        Assert.notNull(city, "You must provide a City name to locate an item in the repository.")
        val list = collegeRepository.findByCity(city!!)
        Assert.notEmpty(list, "City name provided does not contain any College.")
        return list[0]
    }

    @RequestMapping(value = "/discipline/{discipline}", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findCollegesByDiscipline(@PathVariable discipline: String?): List<College> {
        Assert.notNull(discipline, "You must provide a Discipline to locate an item in the repository.")
        val list = collegeRepository.findByDiscipline(discipline!!)
        Assert.notEmpty(list, "Discipline name provided does not map to any College.")
        return list
    }

    @RequestMapping(value = "/{college}/students", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findAllStudentsByCollege(@PathVariable college: String?): List<Student> {
        Assert.notNull(college, "You must provide a College name to locate nested itens in an auxiliary repository.")
        val college = collegeRepository.findOne(college)
        Assert.notNull(college, "Name provided does not correspond to a valid College.")
        return studentRepository.findByCollege(college.name)
    }

    @RequestMapping(value = "/{alias}", method = arrayOf(RequestMethod.DELETE))
    @ResponseBody
    fun delete(@PathVariable alias: String?): String {
        Assert.notNull(alias, "You must provide an Alias to delete an item from the repository.")
        val college = collegeRepository.findOne(alias)
        if (college != null) {
            collegeRepository.delete(college.name)
            return "Item corresponding to the Alias $alias has been deleted."
        } else {
            return "No item found to be deleted."
        }
    }

    @RequestMapping(value = "/{name}", method = arrayOf(RequestMethod.PUT))
    @ResponseBody
    fun update(@PathVariable name: String?, @RequestBody t: College): String {
        Assert.notNull(name, "You must provide an College name to update an item in the repository.")
        Assert.state(t.name == name, "The item you are trying to update does not exist in the repository.")
        t.name = name!!
        return "Item updated: ${collegeRepository.save(t)}."
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody t: College): College {
        Assert.notNull(t.name, "You must provide a full College json to create an item in the repository.")
        val c = collegeRepository.save(t)
        return c
    }
}
