package org.pensatocode.simplicity.sample.repository;

import org.pensatocode.simplicity.jdbc.JdbcRepository;
import org.pensatocode.simplicity.sample.domain.College;

public interface CollegeRepository extends JdbcRepository<College, Long> {
}
