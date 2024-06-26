package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
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
class GetAllExercisesUseCaseTest {

    /**
     * En fait, les uses cases ne font que passe-plat, donc l'intérêt de ces tests unitaires est assez limités à cet instant
     */

    @Mock
    private lateinit var mockedExerciseRepository: ExerciseRepository

    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    private lateinit var closeable : AutoCloseable

    private val lIdUserTest : Long = 1


    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)  DEPRECATED https://www.javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/MockitoAnnotations.html
        closeable = MockitoAnnotations.openMocks(this)
        getAllExercisesUseCase = GetAllExercisesUseCase(mockedExerciseRepository)
    }


    @After
    fun tearDown() {
        //Mockito.framework().clearInlineMocks()
        closeable.close()
    }


    @Test
    fun classic() = runBlocking {
        // Arrange
        val fakeExercises = listOf(
            Exercise(
                startTime = LocalDateTime.now(),
                duration = 30,
                category = ExerciseCategory.Running,
                intensity = 5
            ),
            Exercise(
                startTime = LocalDateTime.now().plusHours(1),
                duration = 45,
                category = ExerciseCategory.Riding,
                intensity = 7
            )
        )
        Mockito.`when`(mockedExerciseRepository.getExercisesOfUser(lIdUserTest)).thenReturn(fakeExercises)


        // Act
        val result = getAllExercisesUseCase.execute(lIdUserTest)


        // Assert
        assertEquals(fakeExercises, result)
    }


    @Test
    fun emptyRepository() = runBlocking {
        // Arrange
        Mockito.`when`(mockedExerciseRepository.getExercisesOfUser(lIdUserTest)).thenReturn(emptyList())


        // Act
        val result = getAllExercisesUseCase.execute(lIdUserTest)


        // Assert
        assertTrue(result.isEmpty())
    }


}