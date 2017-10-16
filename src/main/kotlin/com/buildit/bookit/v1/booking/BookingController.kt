package com.buildit.bookit.v1.booking

import com.buildit.bookit.v1.bookable.BookableNotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime

/**
 * Endpoint to manage bookings
 */
@RestController
@RequestMapping("/v1/booking")
class BookingController {
    val theBooking = Booking(1, 1000, "The Booking", LocalDateTime.now(), LocalDateTime.now())

    /**
     * Get a booking
     */
    @GetMapping(value = "/{id}")
    fun getBooking(@PathVariable("id") bookingId: Int): ResponseEntity<Booking> {
        if (bookingId == 1) {
            return ResponseEntity.ok(theBooking)
        }

        throw BookableNotFound()
    }

    /**
     * Create a booking
     */
    @PostMapping()
    fun createBooking(@RequestBody bookingRequest: BookingRequest): ResponseEntity<Booking> {
        println(bookingRequest)

        val bookingId = 1 + (Math.random() * 999999).toInt()
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Booking(bookingId, bookingRequest.bookableId, bookingRequest.subject, bookingRequest.startDateTime, bookingRequest.endDateTime))
    }
}
