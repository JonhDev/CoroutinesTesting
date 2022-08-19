package com.jonhbravo.coroutinestesting.data

import com.jonhbravo.coroutinestesting.data.UserRepository
import com.jonhbravo.coroutinestesting.models.AccountInformation
import com.jonhbravo.coroutinestesting.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTests {

    private val expectedUser = User(
        name = "Jonathan",
        lastName = "Bravo",
        age = 25
    )

    private val expectedAccountInformation = AccountInformation(
        bankAccount = "12345678",
        address = "Mexico...",
        zipCode = "1234"
    )

    private lateinit var userRepository: UserRepository

    @Before
    fun init() {
        userRepository = UserRepository()
    }

    @Test
    fun `should get an expected user when call to the service`() = runTest {
        val user = userRepository.getUser()

        println(currentTime) //Running in same thread
        assertTrue(user == expectedUser)
    }

    @Test
    fun `should get an expected account information when call to the service`() = runTest {
        val accountInformation = userRepository.getAccountInformation()

        println(currentTime) //Running in same thread
        assertTrue(accountInformation == expectedAccountInformation)
    }
}