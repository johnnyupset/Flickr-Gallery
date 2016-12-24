package it.univr.android.gallery;

import android.support.annotation.UiThread;

import java.util.HashSet;
import java.util.Set;

import it.univr.android.gallery.controller.Controller;
import it.univr.android.gallery.model.Pictures;
import it.univr.android.gallery.view.GalleryLayout;

/**
 * A Model/View/Controller triple. It is a static data structure
 * so that it persist in memory as long as the application is running.
 * In order to avoid view leaks, views must be registered and
 * unregistered when they are shown or hidden, respectively.
 */
public class MVC {

    /**
     * The model of the triple.
     */
    public final static Pictures model = new Pictures();

    /**
     * There might be zero, one or more views bound to the same controller
     * and reflecting the same model. However, in this app it is possible to
     * have only zero or one view at a given moment.
     * Views are not exposed, in order to avoid accidental modifications
     * of the set. Instead, internal iteration is used.
     */
    private final static Set<GalleryLayout> views = new HashSet<>();

    /**
     * The controller of the triple.
     */
    public final static Controller controller = new Controller();

    /**
     * Registers the view.
     *
     * @param view
     */
    @UiThread
    public static void registerView(GalleryLayout view) {
        views.add(view);
    }

    /**
     * Unregisters the view.
     *
     * @param view
     */
    @UiThread
    public static void unregisterView(GalleryLayout view) {
        views.remove(view);
    }

    /**
     * A task that can be executed on all views currently registered.
     */
    public interface ViewTask {

        /**
         * Applies the task to the given view.
         *
         * @param view
         */
        @UiThread
        void process(GalleryLayout view);
    }

    /**
     * Applies the given task to all views currently registered.
     * Since registeration and unregistration occur in the UI thread
     * as well, there is no need to synchronize here.
     *
     * @param task
     */
    @UiThread
    public static void forEachView(ViewTask task) {
        // Internal iteration, preferred since we do not need
        // to expose the modifiable set of views
        for (GalleryLayout view: views)
            task.process(view);
    }
}