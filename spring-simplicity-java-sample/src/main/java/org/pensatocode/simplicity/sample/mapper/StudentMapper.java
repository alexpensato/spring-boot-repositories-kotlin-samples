package org.pensatocode.simplicity.sample.mapper;

import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper;
import org.pensatocode.simplicity.sample.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudentMapper extends TransactionalRowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) {
        try {
            return new Student(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("address")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> mapColumns(Student entity) {
        Map<String, Object> mapping = new LinkedHashMap<>();
        mapping.put("id", entity.getId());
        mapping.put("name", entity.getName());
        mapping.put("address", entity.getAddress());
        return mapping;
    }

    @Override
    public Map<String, Integer> mapTypes() {
        final Map<String, Integer> mapping = new LinkedHashMap<>();
        mapping.put("id", Types.INTEGER);
        mapping.put("name", Types.VARCHAR);
        mapping.put("address", Types.VARCHAR);
        return mapping;
    }
}
