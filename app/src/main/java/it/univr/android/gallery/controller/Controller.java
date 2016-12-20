package it.univr.android.gallery.controller;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    private final AtomicInteger taskCounter = new AtomicInteger();

    public void fetchListOfPictures(Context context, int howMany) {
        taskCounter.incrementAndGet();
        ControllerService.fetchListOfPictures(context, howMany);
    }

    public void fetchPicture(Context context, int position) {
        taskCounter.incrementAndGet();
        ControllerService.fetchPicture(context, position);
    }

    public boolean isIdle() {
        return taskCounter.get() == 0;
    }

    void taskFinished() {
        taskCounter.decrementAndGet();
    }

    void resetTaskCounter() {
        taskCounter.set(0);
    }
}