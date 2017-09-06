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
package net.pensato.simplicity.sample.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.context.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.util.Assert


@Configuration
@EnableTransactionManagement
open class DbConfig {

    @Autowired
    val env: Environment? = null

    @Bean
    open fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    open fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean(destroyMethod = "shutdown")
    @Primary
    open fun dataSource(): DataSource {
        Assert.notNull(env, "Environment properties must not be null to connect to database.")
        val props = Properties()
        props.setProperty("dataSourceClassName", env!!.getProperty("spring.datasource.driver-class-name"))
        props.setProperty("dataSource.serverName", env.getProperty("spring.datasource.server-name"))
        props.setProperty("dataSource.portNumber", env.getProperty("spring.datasource.port-number"))
        props.setProperty("dataSource.databaseName", env.getProperty("spring.datasource.database-name"))
        props.setProperty("dataSource.user", env.getProperty("spring.datasource.user"))
        props.setProperty("dataSource.password", env.getProperty("spring.datasource.password"))

        return HikariDataSource(HikariConfig(props))
    }
}
