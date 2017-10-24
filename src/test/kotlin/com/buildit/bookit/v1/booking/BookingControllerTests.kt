package com.buildit.bookit.v1.booking

import com.buildit.bookit.BookitProperties
import com.buildit.bookit.database.DefaultDataAccess
import com.buildit.bookit.database.DefaultConnectionProvider
import com.buildit.bookit.v1.bookable.dto.BookableNotFound
import com.buildit.bookit.v1.booking.dto.Booking
import com.buildit.bookit.v1.booking.dto.BookingRequest
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.throws
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.time.LocalDateTime

/**
 * Booking controller unit tests
 */
object BookingControllerTests : Spek({
    describe("get all booking") {
        on("invoking getAllBookings()") {
            it("should throw an exception") {
                val startDateTime = LocalDateTime.parse("2017-09-26T09:00:00")
                val endDateTime = LocalDateTime.parse("2017-09-26T10:00:00")

                val mockRepo = mock<BookingRepository> {
                    on { getAllBookings() }.doReturn(listOf(Booking(1, 2, "Booking", startDateTime, endDateTime)))
                }
                val bookings = BookingController(mockRepo).getAllBookings().body
                expect(bookings.size).to.be.equal(1)
            }
        }
    }

    describe("get unknown booking") {
        on("invoking getBooking()") {
            it("should throw an exception") {
                assertThat({ BookingController(BookingDatabaseRepository(DefaultDataAccess(DefaultConnectionProvider(BookitProperties())))).getBooking(2) }, throws<BookableNotFound>())
            }
        }
    }

    describe("create a booking") {
        on("createBooking()") {
            it("should create a booking") {
                val start = LocalDateTime.now().plusHours(1)
                val end = start.plusHours(1)
                val request = BookingRequest(999999, "MyRequest", start, end)
                val response = BookingController(BookingDatabaseRepository(DefaultDataAccess(DefaultConnectionProvider(BookitProperties())))).createBooking(request)
                val booking = response.body

                expect(booking.subject).to.be.equal("MyRequest")
                expect(booking.bookableId).to.be.equal(999999)
                expect(booking.bookingId).to.be.above(0)
            }
        }
    }
})
