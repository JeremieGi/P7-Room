package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class DeleteExerciseUseCaseTest {

    @Mock
    private lateinit var mockedExerciseRepository: ExerciseRepository

    private lateinit var deleteExerciseUseCase : DeleteExerciseUseCase

    private lateinit var closeable : AutoCloseable

    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)  DEPRECATED https://www.javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/MockitoAnnotations.html
        closeable = MockitoAnnotations.openMocks(this)
        deleteExerciseUseCase = DeleteExerciseUseCase(mockedExerciseRepository)
    }


    @After
    fun tearDown() {
        //Mockito.framework().clearInlineMocks()
        closeable.close()
    }

    /**
     * Vérification de l'appel au repository
     */
    @Test
    fun classic() = runBlocking{

        // Given
        val exerciceToDelete = Exercise(
            startTime = LocalDateTime.now().plusHours(3),
            duration = 60,
            category = ExerciseCategory.Riding,
            intensity = 10
        )

        // When
        deleteExerciseUseCase.execute(exerciceToDelete)

        // Then
        // Vérification que la méthode delete du repository a été appelée
        Mockito.verify(mockedExerciseRepository).deleteExercise(exerciceToDelete)


    }


}
