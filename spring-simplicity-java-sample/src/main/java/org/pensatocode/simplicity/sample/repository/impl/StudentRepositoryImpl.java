package org.pensatocode.simplicity.sample.repository.impl;

import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository;
import org.pensatocode.simplicity.sample.domain.Student;
import org.pensatocode.simplicity.sample.mapper.StudentMapper;
import org.pensatocode.simplicity.sample.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository(value = "studentRepository")
public class StudentRepositoryImpl extends AbstractJdbcRepository<Student, Long> implements StudentRepository {

    public StudentRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, new StudentMapper(), "student", Student.class, "id");
    }

    public List<Student> findAllByCollege(Long collegeId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        String queryString = sqlGenerator.selectAll(tableDesc, "college_id = ?", pageable);
        return jdbcTemplate.query(
                queryString,
                new Long[]{collegeId},
                new int[]{Types.INTEGER},
                rowMapper);
    }
}
