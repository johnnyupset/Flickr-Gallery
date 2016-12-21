package it.univr.android.gallery.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univr.android.gallery.MVC;
import it.univr.android.gallery.MVC.ViewTask;
import it.univr.android.gallery.view.GalleryLayout;

public class Pictures {
    private String[] titles;
    private String[] urls;
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public enum Event {
        PICTURES_LIST_CHANGED,
        BITMAP_CHANGED
    };

    public synchronized String[] getTitles() {
        return titles;
    }

    public synchronized Bitmap getBitmap(int position) {
        if (urls == null || position < 0 || position >= urls.length)
            return null;
        else
            return bitmaps.get(urls[position]);
    }

    public synchronized String getUrl(int position) {
        return urls != null && position >= 0 && position < urls.length ? urls[position] : null;
    }

    public void setPictures(Iterable<Picture> pictures) {
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        for (Picture picture: pictures) {
            titles.add(picture.title);
            urls.add(picture.url);
        }

        synchronized (this) {
            this.titles = titles.toArray(new String[titles.size()]);
            this.urls = urls.toArray(new String[urls.size()]);
            this.bitmaps.clear();
        }

        notifyViews(Event.PICTURES_LIST_CHANGED);
    }

    public void setBitmap(String url, Bitmap bitmap) {
        synchronized (this) {
            this.bitmaps.put(url, bitmap);
        }

        notifyViews(Event.BITMAP_CHANGED);
    }

    private void notifyViews(final Event event) {
        final ViewTask viewProcess = new ViewTask() {
            @Override
            public void process(GalleryLayout view) {
                view.onModelChanged(event);
            }
        };

        // Notifies the views. This must be done in the UI thread,
        // since views might have to redraw themselves
        new Handler(Looper.getMainLooper()).post(
                new Runnable() {
                    @Override
                    public void run() {
                        MVC.forEachView(viewProcess);
                    }
                }
        );
    }

}
