package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.database.AsteroidRepository
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    enum class FilterAsteroid {
        TODAY,
        WEEK,
        ALL
    }
    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private var _filter = MutableLiveData(FilterAsteroid.ALL)

    private val _Nasteroid = MutableLiveData<Asteroid>()
    val Nasteroid: MutableLiveData<Asteroid>
        get() = _Nasteroid

    val asteroids = Transformations.switchMap(_filter) {
        when (it!!) {
            FilterAsteroid.WEEK -> asteroidRepository.weekAsteroids
            FilterAsteroid.TODAY -> asteroidRepository.todayAsteroids
            else -> asteroidRepository.allAsteroids
        }
    }

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
            asteroidRepository.refreshPicture()
            _pictureOfDay.value=Network.devbytes.getPicture(Constants.API_KEY)
        }
    }

    fun Detail(asteroid: Asteroid){
        _Nasteroid.value = asteroid
    }
    fun onComplete(){
        _Nasteroid.value = null
    }
    fun Filter(filter: FilterAsteroid) {
        _filter.postValue(filter)
    }
    /**
     */

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
