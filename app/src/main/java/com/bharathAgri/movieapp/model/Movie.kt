package com.bharathAgri.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import org.jetbrains.annotations.Nullable
@Entity(tableName = "movie")
data class Movie(

    val adult: Boolean?,
    //val genre_ids: List<Int>,
    val backdrop_path: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?

)

