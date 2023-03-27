package com.example.homework28.di

import com.example.core.ChildWorkerFactory
import com.example.core.WorkerKey
import com.example.homework28.MyWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(MyWorker::class)
    fun bindMyWorker(factory: MyWorker.Factory): ChildWorkerFactory
}