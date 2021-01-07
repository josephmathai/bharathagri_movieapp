package com.bharathAgri.movieapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bharathAgri.movieapp.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}