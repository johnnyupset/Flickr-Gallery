package it.univr.android.gallery.model;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Looper;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.univr.android.gallery.view.GalleryLayout;

public class Pictures {
    private static Pictures pictures;
    private String[] titles;
    private String[] urls;
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public static enum Event {
        PICTURES_LIST_CHANGED,
        BITMAP_CHANGED
    };

    private Pictures() {}

    public static synchronized Pictures get() {
        if (pictures != null)
            return pictures;
        else
            return pictures = new Pictures();
    }

    private Set<GalleryLayout> views = new HashSet<>();

    public void registerView(GalleryLayout view) {
        views.add(view);
    }

    public void unregisterView(GalleryLayout view) {
        views.remove(view);
    }

    private void notifyViews(final Event event) {
        // Notifies the views. This must be done in the UI thread,
        // since views might have to redraw themselves
        new Handler(Looper.getMainLooper()).post(
            new Runnable() {
                @Override
                public void run() {
                    for (GalleryLayout view: views)
                        view.onModelChanged(event);
                }
            }
        );
    }

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

        notifyViews(Event.PICTURES_LIST_CHANGED);
    }

    public void setBitmap(String url, Bitmap bitmap) {
        this.bitmaps.put(url, bitmap);

        notifyViews(Event.BITMAP_CHANGED);
    }
}
