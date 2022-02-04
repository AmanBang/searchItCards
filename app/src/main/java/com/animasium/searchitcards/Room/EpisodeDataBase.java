package com.animasium.searchitcards.Room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.animasium.searchitcards.AnimeShowEPList.Episode;

@Database(entities = EpisodePOJO.class, version = 1,exportSchema = false)
public abstract class EpisodeDataBase extends RoomDatabase {

    private static final String LOG_TAG = EpisodeDataBase.class.getSimpleName();
    private static final  Object LOCK = new Object();
    private static final String DATABASE_NAME = "epi_db";
    private static EpisodeDataBase sInstance;

    public static EpisodeDataBase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "creating new DatabaseInstance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        EpisodeDataBase.class,DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG,"getting the Database insatance");
        return sInstance;
    }

    public abstract EpisodeDao EpiDao();
}
