package com.t3h.mp3music.model;

import android.provider.MediaStore;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "songs")
public class Song extends BaseModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns._ID)
    protected long id;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.DATA)
    private String data;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.TITLE)
    private String title;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.SIZE)
    private int size;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.DURATION)
    private int duration;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.ARTIST)
    private String artist;
    @FieldInfo(fieldName = MediaStore.Audio.AudioColumns.ALBUM)
    private String album;
    private int isFavorite=0;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public int getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
