package com.example.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.ViewModelFactory
import com.example.core.ViewModelKey
import com.example.feature.SecondViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SecondViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SecondViewModel::class)
    fun bindSecondViewModel(viewModel: SecondViewModel): ViewModel
}