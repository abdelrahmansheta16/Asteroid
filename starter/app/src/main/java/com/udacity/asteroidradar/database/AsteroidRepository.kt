package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.entities.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel

class AsteroidRepository (private val database: AsteroidDatabase){

    var arrdates= getNextSevenDaysFormattedDates()
    val allAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAll())
    {
        it.asDomainModel()
    }

    val weekAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getThisWeek(arrdates[0].toString(), arrdates[6].toString()))
    {
        it.asDomainModel()
    }

    val todayAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getToday(arrdates[0].toString()))
    {
        it.asDomainModel()
    }

    val picture: LiveData<PictureOfDay> = Transformations.map(database.asteroidDao.getPicture())
    {
        it.asDomainModel()
    }



    suspend fun refreshAsteroids()
    {
        withContext(Dispatchers.IO) {
            val listAsteroidModel = Network.devbytes.getAsteroids(API_KEY)
            var asteroidArray= parseAsteroidsJsonResult(listAsteroidModel)
            database.asteroidDao.insertAll(*asteroidArray.asDatabaseModel())

        }
    }
    suspend fun refreshPicture()
    {
        withContext(Dispatchers.IO){
            val pictureOfDayModel = Network.devbytes.getPicture(API_KEY)
            val pictureOfDayEntitity = pictureOfDayModel.asDatabaseModel()
            database.asteroidDao.insertPicture( pictureOfDayEntitity)

        }
    }


}