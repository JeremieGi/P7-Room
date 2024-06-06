package com.openclassrooms.arista

import android.app.Application
import com.openclassrooms.arista.dao.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class MainApplication : Application(){
    companion object {
        // TODO : A expliquer à Denis
        // Je mets l'ID du current user en global de l'application
        // pour coder l'application de façon générique avec plusieurs users possibles dans la base de données
        // Il faudrait que l'interface permette d'abord un log, pour connaître au lancement l'utilisateur courant
        const val ID_CURRENT_USER : Long = 1


    }

    override fun onCreate() {
        super.onCreate()

        // Je fais çà pour qu'au 1er lancement, la base de données soit directement créée
        // ainsi le user 1 est trouvé dès le 1er lancement
       AppDatabase.getDatabase(this, CoroutineScope(Dispatchers.Default))
    }
}
