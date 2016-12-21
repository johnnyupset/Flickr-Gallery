package it.univr.android.gallery.view;

import it.univr.android.gallery.model.Pictures;

public interface GalleryLayout {
    void showPicture(int position);
    void onModelChanged(Pictures.Event event);
}