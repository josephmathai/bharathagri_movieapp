package com.bharathAgri.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bharathAgri.movieapp.model.MovieDetails
import com.bharathAgri.movieapp.network.MovieDetailsNetworkDataSource
import com.bharathAgri.movieapp.network.NetworkState
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : MovieRetroInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}