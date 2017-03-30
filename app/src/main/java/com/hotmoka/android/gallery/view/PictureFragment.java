package com.hotmoka.android.gallery.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.hotmoka.android.gallery.MVC;
import com.hotmoka.android.gallery.R;
import com.hotmoka.android.gallery.model.Pictures;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * A fragment containing the picture currently shown in the
 * Flickr Gallery app.
 */
public abstract class PictureFragment extends Fragment implements GalleryFragment {

    private final static String ARG_POSITION = "position";

    Intent mShareIntent;
    ShareActionProvider mShareActionProvider;
    Uri mPictureUri;

    /**
     * This constructor is called when creating the view for the
     * two panes layout and when recreating the fragment upon
     * configuration change. We ensure that args exist. If they
     * already existed, previous args will be kept by the OS.
     */
    @UiThread
    protected PictureFragment() {
        init(-1);
    }

    @UiThread
    protected void init(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.picture_view, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        initializeShareIntent();
        showPictureOrDownloadIfMissing();
    }

    private void initializeShareIntent() {
        mShareIntent = new Intent();
        // ACTION_SEND indicates we want to send data across apps, AKA sharing an image
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("image/*");
        mShareIntent.putExtra(Intent.EXTRA_STREAM, mPictureUri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.picture_menu, menu);

        // Find the MenuItem that we know has the ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Get its ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Connect the dots: give the ShareActionProvider its Share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }
    }

    /**
     * Shows the picture corresponding to the given position in the list.
     * If missing, it will start a background download task.
     *
     * @param position
     */
    @UiThread
    public void showPicture(int position) {
        getArguments().putInt(ARG_POSITION, position);
        showPictureOrDownloadIfMissing();
    }

    @UiThread
    private void showPictureOrDownloadIfMissing() {
        int position = getArguments().getInt(ARG_POSITION);
        String url;
        if (!showBitmapIfDownloaded(position) && (url = MVC.model.getUrl(position)) != null) {
            ((GalleryActivity) getActivity()).showProgressIndicator();
            MVC.controller.onPictureRequired(getActivity(), url);
        }
    }

    /**
     * Shows the bitmap at the given position in the model, if it is in the model.
     *
     * @param position the position
     * @return true if the bitmap was shown, false otherwise, hence if the
     *         position is illegal or the model does not contain the bitmap yet
     */
    @UiThread
    protected boolean showBitmapIfDownloaded(int position) {
        Bitmap bitmap = MVC.model.getBitmap(position);
        if (bitmap != null) {
            mPictureUri = getPictureUri(getActivity().getApplicationContext(), bitmap);
            Log.d("URI CHECK", "showBitmapIfDownloaded: " + mPictureUri);
            ((ImageView) getView().findViewById(R.id.picture)).setImageBitmap(bitmap);
            return true;
        }
        else
            return false;
    }

    public Uri getPictureUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String picturePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
        Log.d("PATH CHECK", "getPictureUri: " + picturePath);
        return Uri.parse(picturePath);
    }

    @UiThread
    public void onModelChanged(Pictures.Event event) {
        switch (event) {
            case BITMAP_CHANGED: {
                // A new bitmap arrived: update the picture in the view
                showPictureOrDownloadIfMissing();
                break;
            }
            case PICTURES_LIST_CHANGED:
                // Erase the picture shown in the view, since the list of pictures has changed
                ((ImageView) getView().findViewById(R.id.picture)).setImageBitmap(null);
                // Take note that no picture is currently selected
                getArguments().putInt(ARG_POSITION, -1);
                break;
        }
    }
}