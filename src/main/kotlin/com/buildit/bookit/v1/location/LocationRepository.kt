package com.buildit.bookit.v1.location

import com.buildit.bookit.v1.location.dto.Location
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

interface LocationRepository {
    fun getLocations(): Collection<Location>
}

@Repository
class LocationStorageRepository(private val jdbcTemplate: JdbcTemplate) : LocationRepository {

    override fun getLocations(): Collection<Location> = jdbcTemplate.query(
        "SELECT LOCATION_NAME, LOCATION_TZ FROM LOCATION") { rs, _ ->
        Location(rs.getString("LOCATION_NAME"), rs.getString("LOCATION_TZ"))
    }
}
