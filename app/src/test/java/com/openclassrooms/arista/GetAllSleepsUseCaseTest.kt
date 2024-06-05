package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class GetAllSleepsUseCaseTest {

    @Mock
    private lateinit var sleepRepository: SleepRepository

    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase

    private lateinit var closeable : AutoCloseable

    private val lIdUserTest : Long = 1


    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)  DEPRECATED https://www.javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/MockitoAnnotations.html
        closeable = MockitoAnnotations.openMocks(this)
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }


    @After
    fun tearDown() {
        //Mockito.framework().clearInlineMocks()
        closeable.close()
    }


    @Test
    fun classic() = runBlocking {
        // Arrange
        val fakeSleeps = listOf(
            Sleep(
                startTime = LocalDateTime.now().plusHours(1),
                duration = 45,
                quality = 1
            ),
            Sleep(
                startTime = LocalDateTime.now().plusHours(2),
                duration = 600,
                quality = 10
            )
        )
        Mockito.`when`(sleepRepository.sleepsOfUser(lIdUserTest)).thenReturn(fakeSleeps)


        // Act
        val result = getAllSleepsUseCase.execute(lIdUserTest)


        // Assert
        assertEquals(fakeSleeps, result)
    }


    @Test
    fun emptyRepository() = runBlocking {
        // Arrange
        Mockito.`when`(sleepRepository.sleepsOfUser(lIdUserTest)).thenReturn(emptyList())

        // Act
        val result = getAllSleepsUseCase.execute(lIdUserTest)

        // Assert
        assertTrue(result.isEmpty())
    }


}