package com.buildit.bookit.v1.location.dto

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Location response
 */
@Entity
data class Location(
    @Id @GeneratedValue
    val id: Int,
    val name: String,
    val timeZone: String)

/**
 * 404 location not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class LocationNotFound : RuntimeException("Location not found")
