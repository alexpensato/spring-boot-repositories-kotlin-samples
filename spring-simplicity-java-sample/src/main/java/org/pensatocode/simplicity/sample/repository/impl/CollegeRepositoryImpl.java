package org.pensatocode.simplicity.sample.repository.impl;

import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository;
import org.pensatocode.simplicity.sample.domain.College;
import org.pensatocode.simplicity.sample.mapper.CollegeMapper;
import org.pensatocode.simplicity.sample.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "collegeRepository")
public class CollegeRepositoryImpl extends AbstractJdbcRepository<College, Long> implements CollegeRepository {

    public CollegeRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, new CollegeMapper(), "college", College.class, "id");
    }
}