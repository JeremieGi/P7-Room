package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
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

@RunWith(JUnit4::class)
class GetUserUsecaseTest {

    @Mock
    private lateinit var mockedUserRepository: UserRepository

    private lateinit var getUserUsecase : GetUserUsecase

    private lateinit var closeable : AutoCloseable

    private val lIdUserTest : Long = 1

    @Before
    fun setUp() {
        //MockitoAnnotations.initMocks(this)  DEPRECATED https://www.javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/MockitoAnnotations.html
        closeable = MockitoAnnotations.openMocks(this)
        getUserUsecase = GetUserUsecase(mockedUserRepository)
    }


    @After
    fun tearDown() {
        //Mockito.framework().clearInlineMocks()
        closeable.close()
    }

    @Test
    fun userFound() = runBlocking {

        // Arrange
        val fakeUser = User(
              name = "jg",
              email = "jg@free.fr"
            )
        val fakeUserResponse = Result.success(fakeUser)

        Mockito.`when`(mockedUserRepository.getUserById(lIdUserTest)).thenReturn(fakeUserResponse)

        // Act
        val result = getUserUsecase.execute(lIdUserTest)

        // Assert
        assertEquals(fakeUserResponse, result)

    }

    /**
     * Vérification de la propagation de l'exception
     */
    @Test
    fun userException() = runBlocking {

        // Arrange

        val sException = "Database error test"
        val runTimeException = RuntimeException(sException)
        val fakeUserResponse = Result.failure<User>(runTimeException)
        Mockito.`when`(mockedUserRepository.getUserById(lIdUserTest)).thenReturn(fakeUserResponse)

        // Act
        val result = getUserUsecase.execute(lIdUserTest)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(runTimeException, result.exceptionOrNull())


    }


}