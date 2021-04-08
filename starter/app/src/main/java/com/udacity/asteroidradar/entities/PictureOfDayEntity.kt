package com.udacity.asteroidradar.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.PictureOfDay

@Entity(tableName = "picture_table")
data class PictureOfDayEntity constructor(
    @PrimaryKey
    val url: String,
    val title: String,
    val mediaType: String
)


fun PictureOfDayEntity.asDomainModel() = PictureOfDay (
    url = url,
    title = title,
    mediaType = mediaType)