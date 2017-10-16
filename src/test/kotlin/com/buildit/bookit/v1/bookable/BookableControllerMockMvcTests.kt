package com.buildit.bookit.v1.bookable

import com.buildit.bookit.v1.booking.BookingController
import com.buildit.bookit.v1.booking.BookingRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

/**
 * Booking Contoller spring mvc integration tests
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(BookableController::class)
class BookableControllerMockMvcTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper
)
{
    /**
     * Get a booking
     */
    @Test
    fun getExistingBookableTest() {
        // arrange
        // act
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/bookable/1"))

        // assert
        result.andExpect(MockMvcResultMatchers.status().isOk)
        result.andExpect(MockMvcResultMatchers.jsonPath<String>("$.bookableName", Matchers.equalToIgnoringCase("The best bookable ever")))
    }

    /**
     * Fail on non-existent
     */
    @Test
    fun getNonexistentBookableTest() {
        // arrange
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/bookable/999"))

        // assert
        result.andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
