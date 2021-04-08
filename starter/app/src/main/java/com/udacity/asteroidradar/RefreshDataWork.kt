package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroids()
            repository.refreshPicture()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}