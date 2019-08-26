package com.t3h.mp3music.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.t3h.mp3music.model.Song;

//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.room.migration.Migration;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import com.t3h.mp3music.model.Song;
//
//
//@Database(entities = Song.class, version = 3)
//public abstract class AppDatabase extends RoomDatabase {
//
//    private static AppDatabase appDatabase;
//
//    public static AppDatabase getInstance(Context context) {
//        if (appDatabase == null){
//            appDatabase = Room.databaseBuilder(
//                    context,
//                    AppDatabase.class,
//                    "news-database"
//            )
//                    .allowMainThreadQueries()
//                    .addMigrations(MIGRATION_1_2)
//                    .build();
//        }
//        return appDatabase;
//    }
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };
//
//    public abstract NewsDao getNewsDao();
//
@Database(entities = Song.class, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "songs-database"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract SongDao getSongDao();
}



