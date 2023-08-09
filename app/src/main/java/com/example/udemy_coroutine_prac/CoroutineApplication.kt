package com.example.udemy_coroutine_prac

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by K.Kobayashi on 2023/06/23.
 */
@HiltAndroidApp
class CoroutineApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
