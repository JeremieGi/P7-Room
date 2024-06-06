package com.openclassrooms.arista.ui.user


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.MainApplication
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase
) : ViewModel() {

    // Communication avec le fragment via
    private val _userFlow = MutableStateFlow<Result<User>?>(null)
    val userFlow: StateFlow<Result<User>?> = _userFlow.asStateFlow() // Exposé au fragment (read only)

    // On devrait normalement le récupérer via le bundle du fragment
    private val idCurrentUser = MainApplication.ID_CURRENT_USER

    init {
        loadUserData(idCurrentUser) // Utilisateur ajouté à la création de la base
    }

    /**
     * Chargement du user connecté
     */
    private fun loadUserData(id : Long) {

        // Exécution du UseCase dans une coroutine
        viewModelScope.launch(Dispatchers.IO) {

            val result = getUserUsecase.execute(id) // Appel du UseCase
            _userFlow.value = result                // Envoie au Fragment via un StateFlow

        }
    }
}
