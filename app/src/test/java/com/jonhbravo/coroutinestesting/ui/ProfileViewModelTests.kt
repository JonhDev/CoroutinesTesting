package com.jonhbravo.coroutinestesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jonhbravo.coroutinestesting.domain.GetUserInformationUseCase
import com.jonhbravo.coroutinestesting.models.AccountInformation
import com.jonhbravo.coroutinestesting.models.User
import com.jonhbravo.coroutinestesting.models.UserInformation
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileViewModelTests {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getUserInformationUseCase: GetUserInformationUseCase = mockk()
    private val stateObserver: Observer<ProfileState> = mockk()

    private val expectedInformation = UserInformation(
        user = User("User", "Testing", 12),
        accountInformation = AccountInformation("1234", "Mexico", "123")
    )

    private lateinit var viewModel: ProfileViewModel

    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        justRun { stateObserver.onChanged(any()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit loading state when view model init`() = runTest {
        coEvery { getUserInformationUseCase() } coAnswers { expectedInformation }
        viewModel = ProfileViewModel(getUserInformationUseCase)
        viewModel.state.observeForever(stateObserver)

        // use advanceUntilIdle to resume all coroutines until they are done
        testDispatcher.scheduler.advanceUntilIdle()

        verify { stateObserver.onChanged(ProfileState.Loading) }
    }

    @Test
    fun `should emit an information state when view model init`() = runTest {
        coEvery { getUserInformationUseCase() } coAnswers { expectedInformation }
        viewModel = ProfileViewModel(getUserInformationUseCase)
        viewModel.state.observeForever(stateObserver)

        // use advanceUntilIdle to resume all coroutines until they are done
        testDispatcher.scheduler.advanceUntilIdle()

        verify { stateObserver.onChanged(ProfileState.Success(expectedInformation)) }
    }

}