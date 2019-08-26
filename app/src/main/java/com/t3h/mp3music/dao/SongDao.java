package com.t3h.mp3music.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.t3h.mp3music.model.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM songs")
    List<Song> getAll();

    @Query("SELECT * FROM songs WHERE isFavorite = 1")
    List<Song> getSongFavorite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Song...songs);

    @Update
    void updateSong(Song song);

    @Query("SELECT * FROM songs where id = :id")
    Song getSongById(long id);

    @Query("UPDATE songs SET isFavorite =1 WHERE id= :id")
    void setFavorite(long id);


}
