package it.univr.android.gallery.controller;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.univr.android.gallery.model.Pictures;
import it.univr.android.gallery.model.Picture;

class ListOfPicturesFetcher {
    private final static String TAG = ListOfPicturesFetcher.class.getSimpleName();
    private final static String ENDPOINT = "https://api.flickr.com/services/rest/";
    private final static String API_KEY = "388f5641e6dc1ecac49678a156f375df";
    private final static int MAX_TITLE_LENGTH = 40;

    ListOfPicturesFetcher(int howMany) {
        Pictures.get().setPictures(fetchItems(howMany));
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

    private String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    private List<Picture> fetchItems(int howMany) {
        try {
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("extras", "url_z")
                    .appendQueryParameter("per_page", String.valueOf(howMany))
                    .build().toString();

            Log.i(TAG, "Sent query: " + url);
            String xmlString = getUrl(url);
            Log.i(TAG, "Received XML: " + xmlString);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));
            return parseItems(parser);
        }
        catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
            return Collections.<Picture> emptyList();
        }
        catch (XmlPullParserException e) {
            Log.e(TAG, "Failed to parse items", e);
            return Collections.<Picture> emptyList();
        }
    }

    private List<Picture> parseItems(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Picture> items = new ArrayList<>();

        for (int eventType = parser.next(); eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next())
            if (eventType == XmlPullParser.START_TAG && "photo".equals(parser.getName())) {
                String caption = parser.getAttributeValue(null, "title");
                String url = parser.getAttributeValue(null, "url_z");
                if (caption == null || caption.isEmpty() || url == null) // The picture might be missing or not have this size
                    continue;

                if (caption.length() > MAX_TITLE_LENGTH)
                    caption = caption.substring(0, MAX_TITLE_LENGTH - 3) + "...";

                items.add(new Picture(caption, url));
            }

        return items;
    }
}