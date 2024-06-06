package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import junit.framework.TestCase.assertEquals
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
class AddNewExerciseUseCaseTest {

    @Mock
    private lateinit var mockedExerciseRepository: ExerciseRepository

    private lateinit var addNewExerciseUseCase : AddNewExerciseUseCase

    private lateinit var closeable : AutoCloseable

    private val lIdUserTest : Long = 1

    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)  DEPRECATED https://www.javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/MockitoAnnotations.html
        closeable = MockitoAnnotations.openMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(mockedExerciseRepository)
    }


    @After
    fun tearDown() {
        //Mockito.framework().clearInlineMocks()
        closeable.close()
    }

    /**
     * Vérification de la propagation de l'exception depuis le repository
     */
    @Test
    fun classic() = runBlocking{

        // Given
        val exerciceToAdd = Exercise(
            startTime = LocalDateTime.now().plusHours(3),
            duration = 60,
            category = ExerciseCategory.Riding,
            intensity = 10
        )

        val sExceptionInfoTest = "Exception info test"

        // Le repository mocké va lever une Exception
        Mockito.`when`(mockedExerciseRepository.addExercise(exerciceToAdd,lIdUserTest)).thenThrow(RuntimeException(sExceptionInfoTest))

        // On vérifie qu'elle soit bien propagée par le useCase
        try{
            addNewExerciseUseCase.execute(exerciceToAdd,lIdUserTest)
            // TODO : Syntaxe plus élégante que assertEquals("1","2" ?
            assert(false) { "une exception aurait due être propagée ici" }
        }
        catch (e : Exception){
            assertEquals(e.localizedMessage,sExceptionInfoTest)
        }

        // Then
        // Vérification que la méthode add du repository a été appelée
        Mockito.verify(mockedExerciseRepository).addExercise(exerciceToAdd,lIdUserTest)


    }

    @Test
    fun exception() = runBlocking{

        // Given
        val exerciceToAdd = Exercise(
            startTime = LocalDateTime.now().plusHours(3),
            duration = 60,
            category = ExerciseCategory.Riding,
            intensity = 10
        )

        // When
        addNewExerciseUseCase.execute(exerciceToAdd,lIdUserTest)


    }


}