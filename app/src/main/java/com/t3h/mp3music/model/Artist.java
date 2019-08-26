package com.t3h.mp3music.model;

import android.provider.MediaStore;

public class Artist extends BaseModel {
    @FieldInfo(fieldName=MediaStore.Audio.Artists._ID)
    private long id;
    @FieldInfo(fieldName=MediaStore.Audio.Artists.ARTIST)
    private String name;
    @FieldInfo(fieldName = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
    private int numberOfAlbum;
    @FieldInfo(fieldName = MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    private String numberOfTrack;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAlbum() {
        return numberOfAlbum;
    }

    public String getNumberOfTrack() {
        return numberOfTrack;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfAlbum=" + numberOfAlbum +
                ", numberOfTrack='" + numberOfTrack + '\'' +
                '}';
    }
}

