package com.openclassrooms.arista.ui.user

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.MainApplication
import com.openclassrooms.arista.R
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
    private val _userFlow = MutableStateFlow<User?>(null)
    val userFlow: StateFlow<User?> = _userFlow.asStateFlow()

    // On devrait normalement le récupérer via le bundle du fragment
    val idCurrentUser = MainApplication.ID_CURRENT_USER

    init {
        // TODO : Au 1er lancement, ce code s'execute avant la création de la base de données : comment gérer çà ?
        loadUserData(idCurrentUser) // Utilisateur ajouté à la création de la base
    }

    private fun loadUserData(id : Long) {

        // Exécution du UseCase dans une coroutine
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUsecase.execute(id)
            if (user!=null) {
                _userFlow.value = user
            }
            else{
                Log.e("JG","Database vide")
            }

        }
    }
}
