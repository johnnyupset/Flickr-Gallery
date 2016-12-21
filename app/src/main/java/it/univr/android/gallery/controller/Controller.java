package it.univr.android.gallery.controller;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

import it.univr.android.gallery.MVC;
import it.univr.android.gallery.MVC.ViewTask;
import it.univr.android.gallery.view.GalleryLayout;

public class Controller {
    private final AtomicInteger taskCounter = new AtomicInteger();

    public void onPictureRequired(Context context, String url) {
        taskCounter.incrementAndGet();
        ControllerService.fetchPicture(context, url);
    }

    public void onTitlesReloadRequest(Context context) {
        taskCounter.incrementAndGet();
        ControllerService.fetchListOfPictures(context, 40);
    }

    public boolean isIdle() {
        return taskCounter.get() == 0;
    }

    public void onTitleSelected(final int position) {
        MVC.forEachView(new ViewTask() {
            @Override
            public void process(GalleryLayout view) {
                view.showPicture(position);
            }
        });
    }

    void taskFinished() {
        taskCounter.decrementAndGet();
    }

    void resetTaskCounter() {
        taskCounter.set(0);
    }
}