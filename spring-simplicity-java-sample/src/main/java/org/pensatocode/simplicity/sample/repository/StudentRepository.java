package org.pensatocode.simplicity.sample.repository;

import org.pensatocode.simplicity.jdbc.JdbcRepository;
import org.pensatocode.simplicity.sample.domain.Student;

import java.util.List;

public interface StudentRepository extends JdbcRepository<Student, Long> {
    List<Student> findAllByCollege(Long collegeId);
}
