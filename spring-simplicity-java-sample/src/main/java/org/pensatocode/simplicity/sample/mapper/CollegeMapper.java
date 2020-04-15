package org.pensatocode.simplicity.sample.mapper;

import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper;
import org.pensatocode.simplicity.sample.domain.College;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollegeMapper extends TransactionalRowMapper<College> {

    @Override
    public College mapRow(ResultSet rs, int rowNum) {
        try {
            return new College(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("name_of_city")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> mapColumns(College entity) {
        Map<String, Object> mapping = new LinkedHashMap<>();
        mapping.put("id", entity.getId());
        mapping.put("name", entity.getName());
        mapping.put("name_of_city", entity.getNameOfCity());
        return mapping;
    }

    @Override
    public Map<String, Integer> mapTypes() {
        final Map<String, Integer> mapping = new LinkedHashMap<>();
        mapping.put("id", Types.INTEGER);
        mapping.put("name", Types.VARCHAR);
        mapping.put("name_of_city", Types.VARCHAR);
        return mapping;
    }

}
