package com.bharathAgri.movieapp.ui.movielist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bharathAgri.movieapp.model.Movie
import com.bharathAgri.movieapp.network.MovieDataSource
import com.bharathAgri.movieapp.network.MovieDataSourceFactory
import com.bharathAgri.movieapp.network.NetworkState
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : MovieRetroInterface, private val context: Context, private val  type: String) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieLatestList: LiveData<PagedList<Movie>>
    lateinit var movieratedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, context, type, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun fetchLiveLatestMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, context, type, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        movieLatestList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return movieLatestList
    }

    fun fetchLiveratedMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService,context, type, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        movieratedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return movieratedList
    }


    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}