package net.pensato.data.cassandra.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "net.pensato.data.cassandra.sample.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected String getKeyspaceName() {
        return "pensato_sample";
    }
}