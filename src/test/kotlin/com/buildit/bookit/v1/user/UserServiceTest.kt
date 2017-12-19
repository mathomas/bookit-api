package com.buildit.bookit.v1.user

import com.buildit.bookit.auth.UserPrincipal
import com.buildit.bookit.v1.user.dto.User
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.expect
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.dao.EmptyResultDataAccessException

class UserServiceTest {

    @Nested
    inner class `register()` {

        @Test
        fun `user already known`() {
            val knownUser = User("given family", "ext", "sub")

            val userRepository: UserRepository = mock {
                on { findByExternalId("sub") }.thenReturn(knownUser)
            }
            val userService = UserService(userRepository)

            val bookitUser: User = userService.register(UserPrincipal("sub", "given", "family"))
            expect(bookitUser).to.equal(knownUser)
        }

        @Test
        fun `user unknown`() {
            val newlyCreatedUser = User("given family", "ext", "sub")

            val userRepository: UserRepository = mock {
                on { findByExternalId("sub") }.thenThrow(EmptyResultDataAccessException(1))
                on { save(User("given", "family", "sub")) }.thenReturn(newlyCreatedUser)
            }

            val userService = UserService(userRepository)

            val bookitUser: User = userService.register(UserPrincipal("sub", "given", "family"))
            expect(bookitUser).to.equal(newlyCreatedUser)
        }
    }
}
