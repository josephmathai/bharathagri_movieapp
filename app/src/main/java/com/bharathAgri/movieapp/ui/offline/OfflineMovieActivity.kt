package com.bharathAgri.movieapp.ui.offline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharathAgri.movieapp.R
import com.bharathAgri.movieapp.database.DatabaseClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OfflineMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_movie)

        var mcontext = this

        GlobalScope.launch {
            val recyclerView: RecyclerView = findViewById(R.id.movie_list)
            var movieAdapter = MoviesAdapter(mcontext,
                DatabaseClient.getInstance(mcontext).getAppDatabase()
                    .movieDao()
                    .all
            )
            val gridLayoutManager = GridLayoutManager(mcontext, 2)

            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return 2
                }
            };

            recyclerView.layoutManager = gridLayoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = movieAdapter
        }
    }
}