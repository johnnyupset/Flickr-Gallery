package com.hotmoka.android.gallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotmoka.android.gallery.MVC;
import com.hotmoka.android.gallery.R;

/**
 * Created by Riccardo Rizzo on 30/03/2017.
 */

public class ThumbnailArrayAdapter extends ArrayAdapter {

    private String[] titles;

    public ThumbnailArrayAdapter(Context context, int resource, String[] titles) {
        super(context, resource, titles);
        this.titles = titles;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_single_item, parent, false);

        TextView pictureTitle = (TextView) rowView.findViewById(R.id.gallery_title);
        ImageView pictureThumbnail = (ImageView) rowView.findViewById(R.id.gallery_thumbnail);

        pictureTitle.setText(titles[position]);
        //Log.d("TITLES", "getView: " + titles[position]);
        //pictureThumbnail.setImageBitmap(MVC.model.getBitmap(position));
        // TODO check why images do not load if you don't scroll down
        showPictureOrDownloadIfMissing(position, pictureThumbnail);

        return rowView;
    }

    @UiThread
    protected void showPictureOrDownloadIfMissing(int position, ImageView pictureThumbnail) {
        String url;
        if (!showBitmapIfDownloaded(position, pictureThumbnail) && (url = MVC.model.getUrl(position)) != null) {
            MVC.controller.onPictureRequired(this.getContext(), url);
        }
    }

    @UiThread
    protected boolean showBitmapIfDownloaded(int position, ImageView pictureThumbnail) {
        Bitmap bitmap = MVC.model.getBitmap(position);
        if (bitmap != null) {
            pictureThumbnail.setImageBitmap(bitmap);
            return true;
        }
        else
            return false;
    }
}
