package com.example.homework28

import android.app.Application
import com.example.core.HasDependencies
import com.example.core.MyWorkerFactory
import com.example.homework28.di.ApplicationComponent
import com.example.homework28.di.DaggerApplicationComponent
import javax.inject.Inject

class NewsApp : HasDependencies, Application() {

    @Inject
    lateinit var workerFactory: MyWorkerFactory

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override val dependencies: Any
        get() = appComponent

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
//        WorkManager.initialize(
//            this,
//            Configuration.Builder().setWorkerFactory(workerFactory).build()
//        )
    }
}