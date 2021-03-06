package com.bharathAgri.movieapp.network

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bharathAgri.movieapp.model.Movie
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : MovieRetroInterface,private val context: Context, private val  type: String, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, context, type, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }
}