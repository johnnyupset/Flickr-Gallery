package it.univr.android.gallery.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.univr.android.gallery.model.Pictures;

class BitmapFetcher {
    private final static String TAG = BitmapFetcher.class.getSimpleName();

    BitmapFetcher(final String url) {
        AsyncTask<Void, Void, Bitmap> itemsFetcher = new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    Log.d(TAG, "Loading image " + url);
                    byte[] bitmapBytes = getUrlBytes(url);
                    return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                }
                catch (IOException e) {
                    Log.e(TAG, "Error downloading image", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null)
                    Pictures.get().setBitmap(url, bitmap);
            }
        };

        itemsFetcher.execute();
    }

    private byte[] getUrlBytes(String urlSpec) throws IOException {
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
}
