package com.example.homework28.di

import android.content.Context
import com.example.homework28.di.modules.DataModule
import com.example.homework28.di.modules.DatabaseModule
import com.example.homework28.di.modules.NetworkModule
import com.example.homework28.ui.InfoFragment
import com.example.homework28.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class, DataModule::class, NetworkModule::class, ViewModelModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: InfoFragment)

}