package com.udacity.asteroidradar

import com.squareup.moshi.Json
import com.udacity.asteroidradar.entities.PictureOfDayEntity


data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String)

fun PictureOfDay.asDatabaseModel() = PictureOfDayEntity (
    url = url,
    title = title,
    mediaType = mediaType)