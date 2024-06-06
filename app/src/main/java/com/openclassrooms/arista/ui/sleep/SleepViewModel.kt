package com.openclassrooms.arista.ui.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.MainApplication
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(private val getAllSleepsUseCase: GetAllSleepsUseCase) :
    ViewModel() {

    private val _sleeps = MutableStateFlow<List<Sleep>>(emptyList())
    val sleeps: StateFlow<List<Sleep>> = _sleeps.asStateFlow()

    // StateFlow dédié aux erreurs
    private val _errorFlow  = MutableStateFlow<String?>(null)
    val errorFlow : StateFlow<String?> get() = _errorFlow

    // On devrait normalement le récupérer via le bundle du fragment
    private val idCurrentUser = MainApplication.ID_CURRENT_USER

    fun getSleeps() {

        // Depuis une coroutine
        viewModelScope.launch(Dispatchers.IO) {

            try {
                // Alimente le StateFlow
                val sleepList = getAllSleepsUseCase.execute(idCurrentUser)
                _sleeps.value = sleepList
            }
            catch (e : Exception){
                _errorFlow.value = e.message
            }

        }

    }
}
