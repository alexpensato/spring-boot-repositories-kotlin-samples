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
package net.pensato.data.mongodb.sample

import net.pensato.data.mongodb.sample.domain.College
import net.pensato.data.mongodb.sample.domain.Student
import net.pensato.data.mongodb.sample.repository.CollegeRepository
import net.pensato.data.mongodb.sample.repository.StudentRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = arrayOf("net.pensato.data.jpa.sample.repository"))
open class App : SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(App::class.java)
	}

    @Bean
    open fun init(
            collegeRepository: CollegeRepository,
            studentRepository: StudentRepository) = CommandLineRunner {

        val result = collegeRepository.findAll()
        if (result == null || result.toList().isEmpty()) {

            val uc = collegeRepository.save(College(name = "University of California", city = "Berkeley"))
			studentRepository.save(Student(name = "Mark", address = "Telegraph Ave", college = uc))
            studentRepository.save(Student(name = "Susie", address = "Shattuck Ave", college = uc))
            studentRepository.save(Student(name = "Valerie", address = "Euclid Ave", college = uc))

            val harvard = collegeRepository.save(College(name = "Harvard University", city = "Cambridge"))
            studentRepository.save(Student(name = "John", address = "Oxford St", college = harvard))
            studentRepository.save(Student(name = "Mary", address = "Washington St", college = harvard))
            studentRepository.save(Student(name = "Joseph", address = "Everett St", college = harvard))
        }
    }
}

fun main(args: Array<String>) {
	SpringApplication.run(App::class.java, *args)
}



