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

    BitmapFetcher(int position) {
        String url = Pictures.get().getUrl(position);
        if (url == null)
            return;

        try {
            Log.d(TAG, "Loading image " + url);
            byte[] bitmapBytes = getUrlBytes(url);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            if (bitmap != null)
                Pictures.get().setBitmap(url, bitmap);
        }
        catch (IOException e) {
            Log.e(TAG, "Error downloading image", e);
        }
    }

    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return new byte[0];

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0)
                out.write(buffer, 0, bytesRead);

            return out.toByteArray();
        }
        finally {
            if (out != null)
                out.close();

            connection.disconnect();
        }
    }
}
