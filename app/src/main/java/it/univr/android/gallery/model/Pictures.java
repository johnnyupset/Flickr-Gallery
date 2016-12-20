package it.univr.android.gallery.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univr.android.gallery.MVC;

public class Pictures {
    private static Pictures pictures;
    private String[] titles;
    private String[] urls;
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public enum Event {
        PICTURES_LIST_CHANGED,
        BITMAP_CHANGED
    };

    public String[] getTitles() {
        return titles;
    }

    public Bitmap getBitmap(int position) {
        if (urls == null || position < 0 || position >= urls.length)
            return null;
        else
            return bitmaps.get(urls[position]);
    }

    public String getUrl(int position) {
        return urls != null && position >= 0 && position < urls.length ? urls[position] : null;
    }

    public void setPictures(Iterable<Picture> pictures) {
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        for (Picture picture: pictures) {
            titles.add(picture.title);
            urls.add(picture.url);
        }

        this.titles = titles.toArray(new String[titles.size()]);
        this.urls = urls.toArray(new String[urls.size()]);
        this.bitmaps.clear();

        MVC.notifyViews(Event.PICTURES_LIST_CHANGED);
    }

    public void setBitmap(String url, Bitmap bitmap) {
        this.bitmaps.put(url, bitmap);

        MVC.notifyViews(Event.BITMAP_CHANGED);
    }
}
