package com.openclassrooms.arista.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.MainApplication
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {

    private val _exercisesFlow = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesFlow: StateFlow<List<Exercise>> = _exercisesFlow.asStateFlow()

    // StateFlow dédié aux erreurs
    private val _errorFlow  = MutableStateFlow<String?>(null)
    val errorFlow : StateFlow<String?> get() = _errorFlow

    private val idCurrentUser = MainApplication.ID_CURRENT_USER

    init {
        loadAllExercises()
    }

    fun deleteExercise(exercise: Exercise) {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                deleteExerciseUseCase.execute(exercise)
                loadAllExercises()
            }
        }
        catch (e : Exception){
            _errorFlow.value = e.message
        }


    }

    private fun loadAllExercises() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val exercises = getAllExercisesUseCase.execute(idCurrentUser)
                _exercisesFlow.value = exercises
            }
            catch (e : Exception){
                _errorFlow.value = e.message
            }
        }

    }

    fun addNewExercise(exercise: Exercise) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                addNewExerciseUseCase.execute(exercise, idCurrentUser)
                loadAllExercises()
            }
            catch (e : Exception){
                _errorFlow.value = e.message
            }


        }

    }
}
