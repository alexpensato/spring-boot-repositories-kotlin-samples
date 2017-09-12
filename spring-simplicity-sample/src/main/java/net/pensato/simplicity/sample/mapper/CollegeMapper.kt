package net.pensato.simplicity.sample.mapper

import net.pensato.simplicity.jdbc.mapper.TransactionalRowMapper
import net.pensato.simplicity.sample.domain.College
import java.sql.ResultSet
import java.util.LinkedHashMap

object CollegeMapper: TransactionalRowMapper<College> {

    override fun mapRow(rs: ResultSet, rowNum: Int): College {
        val entity = College()
        entity.id = rs.getLong("id")
        entity.name = rs.getString("name")
        entity.city = rs.getString("city")

        return entity
    }

    override fun mapColumns(entity: College): Map<String, Any> {
        val mapping = LinkedHashMap<String, Any>()
        mapping.put("id", entity.id)
        mapping.put("name", entity.name)
        mapping.put("city", entity.city)
        return mapping
    }
}