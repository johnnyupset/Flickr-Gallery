package it.univr.android.gallery;

import android.os.Handler;
import android.os.Looper;

import java.util.HashSet;
import java.util.Set;

import it.univr.android.gallery.controller.Controller;
import it.univr.android.gallery.model.Pictures;
import it.univr.android.gallery.model.Pictures.Event;
import it.univr.android.gallery.view.GalleryLayout;

public class MVC {
    public final static Pictures model = new Pictures();
    private final static Set<GalleryLayout> views = new HashSet<>();
    public final static Controller controller = new Controller();

    public static void registerView(GalleryLayout view) {
        views.add(view);
    }

    public static void unregisterView(GalleryLayout view) {
        views.remove(view);
    }

    public static void notifyViews(final Event event) {
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
}