package com.openclassrooms.arista

import android.app.Application
import com.openclassrooms.arista.dao.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class MainApplication : Application(){



    companion object {

        // Je mets l'ID du current user en global de l'application
        // pour coder l'application de façon générique avec plusieurs users possibles dans la base de données
        // Il faudrait que l'interface permette d'abord un log, pour connaître au lancement l'utilisateur courant
        const val ID_CURRENT_USER : Long = 1

    }

    override fun onCreate() {
        super.onCreate()

        // J'ai le fragment qui charge les données avant que la base de données ait fini de s'initialiser.
       AppDatabase.getDatabase(this, CoroutineScope(Dispatchers.Default))

    }
}
