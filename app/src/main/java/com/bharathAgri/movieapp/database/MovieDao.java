package com.bharathAgri.movieapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bharathAgri.movieapp.model.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Dao
public interface MovieDao {
 
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    List<Movie> getAllOrderByDate();

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    List<Movie> getAllOrderByRating();

    @Insert
    void insertall(Movie movies);
 
    @Delete
    void delete(Movie task);
 
    @Update
    void update(Movie task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertall(@NotNull List<Movie> movieList);
}