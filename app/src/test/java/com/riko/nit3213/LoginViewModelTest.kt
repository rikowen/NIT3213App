package com.riko.nit3213

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riko.nit3213.network.LoginResponse
import com.riko.nit3213.repository.AppRepository
import com.riko.nit3213.ui.login.LoginUiState
import com.riko.nit3213.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val repository: AppRepository = mock()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid credentials emits Success state`() = runTest {
        whenever(repository.login("Riko", "s8117371"))
            .thenReturn(LoginResponse("pokemon"))

        viewModel.login("Riko", "s8117371")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Success)
        assertEquals("pokemon", (state as LoginUiState.Success).keypass)
    }

    @Test
    fun `login with empty username emits Error state`() = runTest {
        viewModel.login("", "s8117371")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LoginUiState.Error)
    }

    @Test
    fun `login with empty password emits Error state`() = runTest {
        viewModel.login("Riko", "")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LoginUiState.Error)
    }

    @Test
    fun `login failure from API emits Error state`() = runTest {
        whenever(repository.login("Riko", "s8117371"))
            .thenThrow(RuntimeException("401 Unauthorized"))

        viewModel.login("Riko", "s8117371")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LoginUiState.Error)
    }

    @Test
    fun `resetState sets state back to Idle`() = runTest {
        whenever(repository.login("Riko", "s8117371"))
            .thenReturn(LoginResponse("pokemon"))
        viewModel.login("Riko", "s8117371")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.resetState()

        assertTrue(viewModel.uiState.value is LoginUiState.Idle)
    }
}