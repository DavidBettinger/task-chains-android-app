package bettinger.david.taskchainapp

import android.app.Application
import android.content.Context
import bettinger.david.taskchainapp.data.db.TaskChainDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}