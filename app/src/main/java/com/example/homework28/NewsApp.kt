package com.example.homework28

import android.app.Application
import com.example.homework28.di.ApplicationComponent
import com.example.homework28.di.DaggerApplicationComponent

class NewsApp : Application() {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}