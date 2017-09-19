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
package net.pensato.data.mongodb.sample.controller

import net.pensato.data.mongodb.sample.domain.College
import net.pensato.data.mongodb.sample.domain.Student
import net.pensato.data.mongodb.sample.repository.CollegeRepository
import net.pensato.data.mongodb.sample.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/university")
class UniversityController @Autowired constructor(val collegeRepository: CollegeRepository, val studentRepository: StudentRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun findAll(pageable: Pageable): Page<College> {
        return collegeRepository.findAll(pageable)
    }

    @RequestMapping(value = "/{alias}", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findByAlias(@PathVariable alias: String?): College {
        Assert.notNull(alias, "You must provide an Alias to locate an item in the repository.")
        val list = collegeRepository.findByAlias(alias)
        Assert.notEmpty(list, "Alias provided does not correspond to a valid College.")
        return list[0]
    }

    @RequestMapping(value = "/{collegeAlias}/students", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun findAllStudentsByCollege(@PathVariable collegeAlias: String?): List<Student> {
        Assert.notNull(collegeAlias, "You must provide an Alias to locate nested itens in an auxiliary repository.")
        val list = collegeRepository.findByAlias(collegeAlias)
        Assert.notEmpty(list, "Alias provided does not correspond to a valid College.")
        return studentRepository.findAllByCollege(list[0])
    }

    @RequestMapping(value = "/{alias}", method = arrayOf(RequestMethod.DELETE))
    @ResponseBody
    fun delete(@PathVariable alias: String?): String {
        Assert.notNull(alias, "You must provide an Alias to delete an item from the repository.")
        val list = collegeRepository.findByAlias(alias)
        if (list.isNotEmpty()) {
            collegeRepository.delete(list[0])
            return "Item corresponding to the Alias $alias has been deleted."
        } else {
            return "No item found to be deleted."
        }
    }

    @RequestMapping(value = "/{alias}", method = arrayOf(RequestMethod.PUT))
    @ResponseBody
    fun update(@PathVariable alias: String?, @RequestBody t: College): String {
        Assert.notNull(alias, "You must provide an Alias to update an item in the repository.")
        Assert.state(t.alias == alias, "The item you are trying to update does not exist in the repository.")
        t.alias = alias!!
        return "Item updated: ${collegeRepository.save(t)}."
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody t: College): College {
        if (t.alias == "") {
            t.alias = Companion.nextAlias()
        }
        val c = collegeRepository.save(t)
        return c
    }

    companion object {
        var autoAlias = 0
        fun nextAlias(): String {
            val i = autoAlias++
            return i.toString()
        }
    }

}
