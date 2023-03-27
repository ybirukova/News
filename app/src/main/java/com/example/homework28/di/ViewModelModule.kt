package com.example.homework28.di

import androidx.lifecycle.ViewModel
import com.example.homework28.ui.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @com.example.core.ViewModelKey(NewsViewModel::class)
    fun bindMainViewModel(viewModel: NewsViewModel): ViewModel
}