package it.univr.android.gallery.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.univr.android.gallery.MVC;

class BitmapFetcher {
    private final static String TAG = BitmapFetcher.class.getSimpleName();

    BitmapFetcher(int position) {
        Bitmap bitmap = null;
        String url = null;

        try {
            url = MVC.model.getUrl(position);
            if (url == null)
                return;

            Log.d(TAG, "Loading image " + url);
            byte[] bitmapBytes = getUrlBytes(url);
            bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        }
        catch (IOException e) {
            Log.e(TAG, "Error downloading image", e);
        }
        finally {
            MVC.controller.taskFinished();
        }

        if (bitmap != null)
            MVC.model.setBitmap(url, bitmap);
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
