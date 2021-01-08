package com.bharathAgri.movieapp.ui.offline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharathAgri.movieapp.R
import com.bharathAgri.movieapp.database.DatabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineMovieActivity : AppCompatActivity() {

    lateinit var mcontext: OfflineMovieActivity
    lateinit var movieAdapter: MoviesAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_movie)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mcontext = this
        recyclerView = findViewById(R.id.movie_list)

        GlobalScope.launch(Dispatchers.IO) {
            val movies = DatabaseClient.getInstance(mcontext).getAppDatabase()
                .movieDao()
                .all
            withContext(Dispatchers.Main) {
                movieAdapter = MoviesAdapter(mcontext, movies )
                val gridLayoutManager = GridLayoutManager(mcontext, 2)

                recyclerView.layoutManager = gridLayoutManager
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = movieAdapter
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.offline_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_sort_date ->{
                GlobalScope.launch(Dispatchers.IO) {
                    val movies = DatabaseClient.getInstance(mcontext).getAppDatabase()
                        .movieDao()
                        .allOrderByDate
                    withContext(Dispatchers.Main) {
                        movieAdapter.sortedList( movies)
                        recyclerView.scrollToPosition(0)
                    }
                }
                return true
            }
            R.id.action_sort_rate ->{
                GlobalScope.launch(Dispatchers.IO) {
                    val movies = DatabaseClient.getInstance(mcontext).getAppDatabase()
                        .movieDao()
                        .allOrderByRating
                    withContext(Dispatchers.Main) {
                        movieAdapter.sortedList( movies)
                        recyclerView.scrollToPosition(0)
                    }
                }
                return true
            }
            R.id.action_clear ->{
                GlobalScope.launch(Dispatchers.IO) {
                    val movies = DatabaseClient.getInstance(mcontext).getAppDatabase()
                        .movieDao()
                        .all
                    withContext(Dispatchers.Main) {
                        movieAdapter.sortedList( movies)
                        recyclerView.scrollToPosition(0)
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}