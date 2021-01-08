package com.bharathAgri.movieapp.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bharathAgri.movieapp.database.DatabaseClient
import com.bharathAgri.movieapp.model.Movie
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService : MovieRetroInterface, private  val context: Context, private val type: String, private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Movie>(){

    private var page = 1

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        if (type.equals("popular")) {
            compositeDisposable.add(
                apiService.getPopularMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page+1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
        } else if(type.equals("latest")) {
            compositeDisposable.add(
                apiService.getLatestMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page+1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
        } else if (type.equals("rating")) {
            compositeDisposable.add(
                apiService.getTopRatedMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page+1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        if (type.equals("popular")) {
            compositeDisposable.add(
                apiService.getPopularMovie(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if(it.totalPages >= params.key) {
                                DatabaseClient.getInstance(context).getAppDatabase()
                                    .movieDao()
                                    .insertall(it.movieList)
                                callback.onResult(it.movieList, params.key+1)
                                networkState.postValue(NetworkState.LOADED)
                            }
                            else{
                                networkState.postValue(NetworkState.ENDOFLIST)
                            }
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
        } else if(type.equals("latest")) {
            compositeDisposable.add(
                apiService.getLatestMovie(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if(it.totalPages >= params.key) {
                                DatabaseClient.getInstance(context).getAppDatabase()
                                    .movieDao()
                                    .insertall(it.movieList)
                                callback.onResult(it.movieList, params.key+1)
                                networkState.postValue(NetworkState.LOADED)
                            }
                            else{
                                networkState.postValue(NetworkState.ENDOFLIST)
                            }
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
        } else if (type.equals("rating")) {
            compositeDisposable.add(
                apiService.getTopRatedMovie(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if(it.totalPages >= params.key) {
                                DatabaseClient.getInstance(context).getAppDatabase()
                                    .movieDao()
                                    .insertall(it.movieList)
                                callback.onResult(it.movieList, params.key+1)
                                networkState.postValue(NetworkState.LOADED)
                            }
                            else{
                                networkState.postValue(NetworkState.ENDOFLIST)
                            }
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

}