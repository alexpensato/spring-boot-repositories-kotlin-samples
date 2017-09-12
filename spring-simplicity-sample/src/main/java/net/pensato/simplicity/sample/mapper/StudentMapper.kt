package net.pensato.simplicity.sample.mapper

import net.pensato.simplicity.jdbc.mapper.TransactionalRowMapper
import net.pensato.simplicity.sample.domain.Student
import java.sql.ResultSet
import java.util.LinkedHashMap

object StudentMapper: TransactionalRowMapper<Student> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Student {
        val entity = Student()
        entity.id = rs.getLong("id")
        entity.name = rs.getString("name")
        entity.address = rs.getString("address")

        return entity
    }

    override fun mapColumns(entity: Student): Map<String, Any> {
        val mapping = LinkedHashMap<String, Any>()
        mapping.put("id", entity.id)
        mapping.put("name", entity.name)
        mapping.put("address", entity.address)
        return mapping
    }
}
