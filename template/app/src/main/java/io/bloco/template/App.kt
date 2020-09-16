package io.bloco.template

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class App : Application() {

    val mode by lazy {
        try {
            classLoader.loadClass("io.bloco.template.AppTest")
            Mode.Test
        } catch (e: ClassNotFoundException) {
            Mode.Normal
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupStrictMode()
        setupTimber()
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    enum class Mode { Normal, Test }

}