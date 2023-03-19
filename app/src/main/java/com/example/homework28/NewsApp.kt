package com.example.homework28

import android.app.Application
import com.example.core.HasDependencies
import com.example.homework28.di.ApplicationComponent
import com.example.homework28.di.DaggerApplicationComponent

class NewsApp : Application(), HasDependencies {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override val dependencies: Any
        get() = appComponent
}