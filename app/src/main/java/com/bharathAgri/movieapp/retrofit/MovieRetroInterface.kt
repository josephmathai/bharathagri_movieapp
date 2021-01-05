package com.bharathAgri.movieapp.retrofit

import com.bharathAgri.movieapp.model.MovieDetails
import com.bharathAgri.movieapp.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieRetroInterface {
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/now_playing")
    fun getLatestMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}
