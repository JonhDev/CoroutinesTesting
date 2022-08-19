package com.jonhbravo.coroutinestesting.domain

import com.jonhbravo.coroutinestesting.data.UserRepository
import com.jonhbravo.coroutinestesting.getTestDispatcher
import com.jonhbravo.coroutinestesting.models.AccountInformation
import com.jonhbravo.coroutinestesting.models.User
import com.jonhbravo.coroutinestesting.models.UserInformation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class UserInformationUseCasesTests {

    private val repository: UserRepository = mockk()

    private val mockUser = User(
        name = "Jonathan",
        lastName = "Bravo",
        age = 25
    )

    private val mockAccountInformation = AccountInformation(
        bankAccount = "12345678",
        address = "Mexico...",
        zipCode = "1234"
    )

    private val mockDelay = 1_000L

    private val expectedInformation = UserInformation(mockUser, mockAccountInformation)

    @Test
    fun `should get an expected user information when call to the use case`() = runTest {
        coEvery { repository.getUser() } returns mockUser
        coEvery { repository.getAccountInformation() } returns mockAccountInformation
        val getInformationUseCase = GetUserInformationUseCase(repository, getTestDispatcher())

        val information = getInformationUseCase()

        println(currentTime)
        Assert.assertTrue(expectedInformation == information)
    }

    @Test
    fun `should get an expected information in 1 sec when call to the use case`() = runTest {
        coEvery { repository.getUser() } coAnswers {
            delay(mockDelay)
            mockUser
        }
        coEvery { repository.getAccountInformation() } coAnswers {
            delay(mockDelay)
            mockAccountInformation
        }
        //Use getTestDispatcher to get the StandardTestDispatcher from the TestScope of runTest
        //This is helpful to get the continuation when we switch dispatchers
        val getInformationUseCase = GetUserInformationUseCase(repository, getTestDispatcher())

        val information = getInformationUseCase()

        Assert.assertEquals(mockDelay, currentTime)
        Assert.assertTrue(expectedInformation == information)
    }

}