package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.entities.AsteroidEntity
import com.udacity.asteroidradar.entities.PictureOfDayEntity

@Database(entities = [AsteroidEntity::class, PictureOfDayEntity::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase()
{
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid_database").build()
        }
    }
    return INSTANCE
}