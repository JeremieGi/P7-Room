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

    // On devrait normalement le récupérer via le bundle du fragment
    val idCurrentUser = MainApplication.ID_CURRENT_USER

    fun fetchSleeps() {

        // Depuis une coroutine
        viewModelScope.launch(Dispatchers.IO) {
            // Alimente le StateFlow
            val sleepList = getAllSleepsUseCase.execute(idCurrentUser)
            _sleeps.value = sleepList
        }

    }
}
