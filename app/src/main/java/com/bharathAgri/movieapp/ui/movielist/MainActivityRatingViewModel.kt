package com.bharathAgri.movieapp.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bharathAgri.movieapp.model.Movie
import com.bharathAgri.movieapp.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityRatingViewModel (private val movieRepository : MoviePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieRatedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveratedMoviePagedList(compositeDisposable)
    }
    val  networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return movieRatedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
