package com.t3h.mp3music.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.t3h.mp3music.model.Album;
import com.t3h.mp3music.model.Artist;
import com.t3h.mp3music.model.BaseModel;
import com.t3h.mp3music.model.FieldInfo;
import com.t3h.mp3music.model.Song;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SystemDataUtils {
    private ContentResolver resolver;

    public SystemDataUtils(Context context) {
        resolver = context.getContentResolver();
    }

    public ArrayList<Song> getSongs() {
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        return getData(cursor, Song.class);
    }
    public ArrayList<Song> getSongByAlbum(String albumID) {
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                "ALBUM_ID = " + albumID,
                null,
                null);
        return getData(cursor, Song.class);
    }

    public ArrayList<Artist> getArtists() {
        Cursor cursor = resolver.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                null, null, null, null
        );
        return getData(cursor, Artist.class);

    }

    public ArrayList<Album> getAlbums() {
        Cursor cursor = resolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                null, null, null, null

        );
        return getData(cursor, Album.class);
    }

    private <T extends BaseModel> ArrayList<T> getData(
            Cursor cursor, Class<T> cls) {
        ArrayList<T> data = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            T item = null;
            try {
                item = getRow(cursor, cls);
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.add(item);
            cursor.moveToNext();

        }
        return data;
    }

    private <T extends BaseModel> T getRow(Cursor cursor, Class<T> cls) throws Exception {
        Field[] fields = cls.getDeclaredFields();
        T t = cls.newInstance();
        for (Field f : fields) {
            f.setAccessible(true);
            FieldInfo info = f.getAnnotation(FieldInfo.class);
            if (info == null) {
                continue;
            }
            int index = cursor.getColumnIndex(info.fieldName());
            String value = cursor.getString(index);
            mapValue(t, value, cls, f);
        }
        return t;
    }

    private <T extends BaseModel> void mapValue(T t, String value, Class<T> cls, Field f) throws Exception {
        String type = f.getType().getSimpleName();
        if (type.equalsIgnoreCase("int")) {
            f.setInt(t, Integer.parseInt(value));
            return;
        }
        if (type.equalsIgnoreCase(Long.class.getSimpleName())) {
            f.setLong(t, Long.parseLong(value));
            return;
        }
        if (type.equalsIgnoreCase(Boolean.class.getSimpleName())) {
            f.setBoolean(t, Boolean.parseBoolean(value));
            return;
        }
        if (type.equalsIgnoreCase(Float.class.getSimpleName())) {
            f.setFloat(t, Float.parseFloat(value));
            return;
        }
        if (type.equalsIgnoreCase(Double.class.getSimpleName())) {
            f.setDouble(t, Double.parseDouble(value));
            return;
        }
        f.set(t, value);
    }
}
