package it.univr.android.gallery.controller;

import android.graphics.Bitmap;
import android.widget.ImageView;

import it.univr.android.gallery.model.Pictures;

public class Controller {
    private static Controller controller;

    private Controller() {}

    public static synchronized Controller get() {
        if (controller != null)
            return controller;
        else
            return controller = new Controller();
    }

    public void fetchListOfPictures() {
        new ListOfPicturesFetcher(30);
    }

    public void fetchPicture(int position) {
        String url = Pictures.get().getUrl(position);
        if (url != null)
            new BitmapFetcher(url);
    }
}