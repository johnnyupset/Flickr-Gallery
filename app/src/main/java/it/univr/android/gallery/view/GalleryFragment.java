package it.univr.android.gallery.view;

import android.support.annotation.UiThread;

import it.univr.android.gallery.model.Pictures;

public interface GalleryFragment {

    @UiThread
    void onModelChanged(Pictures.Event event);
}
