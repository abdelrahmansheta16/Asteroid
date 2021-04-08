package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.entities.AsteroidEntity
import com.udacity.asteroidradar.entities.PictureOfDayEntity

@Dao
interface AsteroidDao
{
    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getThisWeek(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table  WHERE closeApproachDate = :todayDate ORDER BY closeApproachDate ASC")
    fun getToday(todayDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table  ORDER BY closeApproachDate ASC")
    fun getAll(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("SELECT * FROM picture_table")
    fun getPicture(): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(picture: PictureOfDayEntity)
}