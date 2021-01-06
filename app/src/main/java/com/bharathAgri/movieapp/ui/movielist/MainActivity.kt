package com.bharathAgri.movieapp.ui.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bharathAgri.movieapp.R
import com.bharathAgri.movieapp.network.NetworkState
import com.bharathAgri.movieapp.retrofit.MovieRetroClient
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var viewModelPopular: MainActivityPopularViewModel
    private lateinit var viewModelLatest: MainActivityLatestViewModel
    private lateinit var viewModelRating: MainActivityRatingViewModel

    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializedata("popular")

    }


    fun initializedata(type: String) {
        val apiService : MovieRetroInterface = MovieRetroClient.getClient()

        movieRepository = MoviePagedListRepository(apiService, type)



        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1
                else return 2
            }
        };


        movie_list.layoutManager = gridLayoutManager
        movie_list.setHasFixedSize(true)
        movie_list.adapter = movieAdapter


        if (type.equals("popular")) {
            viewModelPopular = getViewModel()
            viewModelPopular.moviePagedList.observe(this, Observer {
                movieAdapter.submitList(it)
            })

            viewModelPopular.networkState.observe(this, Observer {
                progressbar_main.visibility = if (viewModelPopular.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txt_error_main.visibility = if (viewModelPopular.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

                if (!viewModelPopular.listIsEmpty()) {
                    movieAdapter.setNetworkState(it)
                }
            })
        } else if(type.equals("latest")) {
            viewModelLatest = getLatestViewModel()
            viewModelLatest.movieLatestList.observe(this, Observer {
                movieAdapter.submitList(it)
            })

            viewModelLatest.networkState.observe(this, Observer {
                progressbar_main.visibility = if (viewModelLatest.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txt_error_main.visibility = if (viewModelLatest.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

                if (!viewModelLatest.listIsEmpty()) {
                    movieAdapter.setNetworkState(it)
                }
            })
        } else if (type.equals("rating")) {
            viewModelRating = getRatingViewModel()
            viewModelRating.movieRatedList.observe(this, Observer {
                movieAdapter.submitList(it)
            })

            viewModelRating.networkState.observe(this, Observer {
                progressbar_main.visibility = if (viewModelRating.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txt_error_main.visibility = if (viewModelRating.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

                if (!viewModelRating.listIsEmpty()) {
                    movieAdapter.setNetworkState(it)
                }
            })

        }



    }
    private fun getViewModel(): MainActivityPopularViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityPopularViewModel(movieRepository) as T
            }
        })[MainActivityPopularViewModel::class.java]
    }

    private fun getLatestViewModel(): MainActivityLatestViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityLatestViewModel(movieRepository) as T
            }
        })[MainActivityLatestViewModel::class.java]
    }

    private fun getRatingViewModel(): MainActivityRatingViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityRatingViewModel(movieRepository) as T
            }
        })[MainActivityRatingViewModel::class.java]
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popular -> {
               initializedata("popular")
                true
            }
            R.id.action_latest ->{
               initializedata("latest")
                return true
            }
            R.id.action_rating ->{
                initializedata("rating")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
