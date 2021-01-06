package com.bharathAgri.movieapp.ui.movieDetails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bharathAgri.movieapp.R
import com.bharathAgri.movieapp.model.MovieDetails
import com.bharathAgri.movieapp.network.NetworkState
import com.bharathAgri.movieapp.retrofit.IMAGE_BASE_URL
import com.bharathAgri.movieapp.retrofit.MovieRetroClient
import com.bharathAgri.movieapp.retrofit.MovieRetroInterface
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.text.NumberFormat
import java.util.*


@Suppress("DEPRECATION")
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : MovieRetroInterface = MovieRetroClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = IMAGE_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

    }

    private fun getViewModel(movieId:Int): DetailMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailMovieViewModel(movieRepository,movieId) as T
            }
        })[DetailMovieViewModel::class.java]
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
               onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

