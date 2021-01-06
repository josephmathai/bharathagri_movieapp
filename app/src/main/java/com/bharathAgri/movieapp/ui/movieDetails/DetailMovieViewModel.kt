package com.bharathAgri.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bharathAgri.movieapp.model.MovieDetails
import com.bharathAgri.movieapp.network.MovieDetailsNetworkDataSource
import com.bharathAgri.movieapp.network.NetworkState
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}