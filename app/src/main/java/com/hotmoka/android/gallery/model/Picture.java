package com.hotmoka.android.gallery.model;

public class Picture {
    public final String title;
    public final String url;
    // Added low resolution url for array adapter thumbnails
    public final String lowResUrl;

    public Picture(String title, String url, String lowResUrl) {
        this.title = title;
        this.url = url;
        this.lowResUrl = lowResUrl;
    }
}
