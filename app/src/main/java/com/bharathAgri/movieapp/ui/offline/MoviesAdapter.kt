package com.bharathAgri.movieapp.ui.offline

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bharathAgri.movieapp.R
import com.bharathAgri.movieapp.model.Movie
import com.bharathAgri.movieapp.retrofit.IMAGE_BASE_URL
import com.bharathAgri.movieapp.ui.movieDetails.MovieDetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

class MoviesAdapter( val context: Context,private var moviesList: List<Movie>) :
   RecyclerView.Adapter<MoviesAdapter.MovieItemViewHolder>() {

   @NonNull
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
      val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item, parent, false)
      return MovieItemViewHolder(itemView)
   }
   override fun onBindViewHolder(itemView: MovieItemViewHolder, position: Int) {
      (itemView as MovieItemViewHolder).bind(moviesList.get(position),context)
   }

    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

       fun bind(movie: Movie?,context: Context) {
          itemView.movie_title.text = movie?.title
          itemView.movie_release_date.text =  movie?.release_date
          itemView.movie_rating.text = movie?.vote_average.toString()

          val moviePosterURL = IMAGE_BASE_URL + movie?.poster_path
          Glide.with(itemView.context)
             .load(moviePosterURL)
             .into(itemView.image_movie_poster)

          itemView.setOnClickListener{
             val intent = Intent(context, MovieDetailActivity::class.java)
             intent.putExtra("id", movie?.id)
             context.startActivity(intent)
             Toast.makeText(context, "Clicked " +movie?.title, Toast.LENGTH_LONG).show()
          }
       }
    }


   override fun getItemCount(): Int {
      return moviesList.size
   }

}