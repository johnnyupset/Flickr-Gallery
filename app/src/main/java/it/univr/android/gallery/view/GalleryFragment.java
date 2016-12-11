package it.univr.android.gallery.view;

import it.univr.android.gallery.model.Pictures;

public interface GalleryFragment {
    void onModelChanged(Pictures.Event event);
}
