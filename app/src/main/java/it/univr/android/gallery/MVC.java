package it.univr.android.gallery;

import java.util.HashSet;
import java.util.Set;

import it.univr.android.gallery.controller.Controller;
import it.univr.android.gallery.model.Pictures;
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

    public interface ViewTask {
        void process(GalleryLayout view);
    }

    public static void forEachView(ViewTask task) {
        // Internal iteration, preferred since we do not need
        // to expose the modifiable set of views.
        // Eventually, lambda expressions should be used here
        for (GalleryLayout view: views)
            task.process(view);
    }
}