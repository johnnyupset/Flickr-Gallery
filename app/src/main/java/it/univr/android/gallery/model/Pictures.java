package it.univr.android.gallery.model;

import java.util.HashSet;
import java.util.Set;

import it.univr.android.gallery.view.GalleryLayout;

public class Pictures {

    private static Set<GalleryLayout> views = new HashSet<>();

    public static void registerView(GalleryLayout view) {
        views.add(view);
    }

    public static void unregisterView(GalleryLayout view) {
        views.remove(view);
    }

    private static void notifyViews() {
        for (GalleryLayout view: views)
            view.onModelChanged();
    }

    public static String[] getTitles() {
        return titles;
    }

    public static void setTitles(String[] titles) {
        Pictures.titles = titles;
        notifyViews();
    }

    private static String[] titles;

    public static String getFilenameFor(int num) {
        return fileNames[num % fileNames.length];
    }

    private static String[] fileNames = {
            "gatto-verde.jpg",
            "il-gufo-inclina-la-testa.jpg",
            "monte-bianco.jpg"
    };
}
