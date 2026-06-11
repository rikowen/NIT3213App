package com.riko.nit3213

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riko.nit3213.network.DashboardResponse
import com.riko.nit3213.network.Entity
import com.riko.nit3213.repository.AppRepository
import com.riko.nit3213.ui.dashboard.DashboardUiState
import com.riko.nit3213.ui.dashboard.DashboardViewModel
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
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val repository: AppRepository = mock()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDashboard success emits list of entities`() = runTest {
        val fakeEntities = listOf(
            Entity("A water-type Pokemon known for its shell."),
            Entity("A fire-type Pokemon that evolves into Charizard.")
        )
        whenever(repository.getDashboard("pokemon"))
            .thenReturn(DashboardResponse(fakeEntities, 2))

        viewModel.loadDashboard("pokemon")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Success)
        assertEquals(2, (state as DashboardUiState.Success).entities.size)
    }

    @Test
    fun `loadDashboard failure emits Error state`() = runTest {
        whenever(repository.getDashboard("pokemon"))
            .thenThrow(RuntimeException("Network error"))

        viewModel.loadDashboard("pokemon")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Error)
        assertEquals("Network error", (state as DashboardUiState.Error).message)
    }

    @Test
    fun `loadDashboard initially emits Loading state`() = runTest {
        whenever(repository.getDashboard("pokemon"))
            .thenReturn(DashboardResponse(emptyList(), 0))

        viewModel.loadDashboard("pokemon")

        assertTrue(viewModel.uiState.value is DashboardUiState.Loading)
    }
}