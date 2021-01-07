package com.bharathAgri.movieapp.database;

import androidx.paging.PagedList;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.Update;

import com.bharathAgri.movieapp.model.Movie;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Dao
public interface MovieDao {
 
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Insert
    void insertall(Movie movies);
 
    @Delete
    void delete(Movie task);
 
    @Update
    void update(Movie task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertall(@NotNull List<Movie> movieList);
}

class RoomConverterNullString {
    @TypeConverter
    public static String fromNullToString(String value) {
        return (value == null) ? "" : value;
    }
}