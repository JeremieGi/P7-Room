package com.openclassrooms.arista

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(){
    companion object {
        // Je mets l'ID du current user en global de l'application
        // pour coder l'application de façon générique avec plusieurs users possibles dans la base de données
        public const val ID_CURRENT_USER : Long = 1
    }
}
