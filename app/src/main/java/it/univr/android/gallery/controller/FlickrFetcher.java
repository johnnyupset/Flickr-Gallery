package it.univr.android.gallery.controller;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlickrFetcher {
    private final static String TAG = FlickrFetcher.class.getSimpleName();
    private final static String ENDPOINT = "https://api.flickr.com/services/rest/";
    private final static String API_KEY = "";

    public static void fetchLastTitles(int howMany) {
        AsyncTask<Void, Void, Void> itemsFetcher = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                fetchItems();
                return null;
            }
        };
    }

    private static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return new byte[0];

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0)
                out.write(buffer, 0, bytesRead);

            out.close();
            return out.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }

    private static String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    private static void fetchItems() {
        try {
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();

            String xmlString = getUrl(url);
            Log.i(TAG, "Received XML: " + xmlString);
        }
        catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }
    }
}