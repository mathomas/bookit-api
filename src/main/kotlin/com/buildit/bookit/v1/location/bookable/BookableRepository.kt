package com.buildit.bookit.v1.location.bookable

import com.buildit.bookit.v1.location.bookable.dto.Bookable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

interface BookableRepository {
    fun getAllBookables(): Collection<Bookable>
}

@Repository
class BookableDatabaseRepository(private val jdbcTemplate: JdbcTemplate) : BookableRepository {
    private val tableName = "BOOKABLE"

    override fun getAllBookables(): Collection<Bookable> = jdbcTemplate.query(
        "SELECT BOOKABLE_ID, LOCATION_ID, BOOKABLE_NAME, BOOKABLE_STATUS FROM $tableName") { rs, _ ->

        Bookable(
            rs.getInt("BOOKABLE_ID"),
            rs.getInt("LOCATION_ID"),
            rs.getString("BOOKABLE_NAME"),
            rs.getBoolean("BOOKABLE_STATUS")
        )
    }
}