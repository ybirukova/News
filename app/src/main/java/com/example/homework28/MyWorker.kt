package com.example.homework28

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.core.ChildWorkerFactory
import com.example.data.NewsListRepositoryImpl
import com.example.domain.repository.NewsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class MyWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val newsListRepository: NewsListRepository
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val result = withContext(Dispatchers.IO) {
                newsListRepository.getNewsList(true)
            }
            if (result.isNullOrEmpty())
                Result.retry()
            else
                Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    class Factory @Inject constructor(
        private val foo: Provider<NewsListRepositoryImpl>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return MyWorker(
                appContext,
                params,
                foo.get()
            )
        }
    }
}