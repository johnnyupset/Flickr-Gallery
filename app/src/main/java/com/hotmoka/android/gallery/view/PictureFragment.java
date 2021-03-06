package com.hotmoka.android.gallery.view;

import android.app.Fragment;
import android.content.ContentValues;
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

import java.io.OutputStream;

/**
 * A fragment containing the picture currently shown in the
 * Flickr Gallery app.
 */
public abstract class PictureFragment extends Fragment implements GalleryFragment {

    private final static String ARG_POSITION = "position";

    // NEW!! Members data needed for share feature
    private final static int SHARE_REQUEST = 0;
    private Intent shareIntent;
    private MenuItem shareButton;
    private Uri pictureUri;

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
    }

    /**
     * Create the OptionsMenu with the share button
     */
    // NEW!!
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.picture_menu, menu);

        // Find the MenuItem button used for sharing
        shareButton = menu.findItem(R.id.menu_item_share);

        // Visibility on false so that on tablets it's visible only when the user click on at least one link
        shareButton.setVisible(false);

        // Visualize picture
        showPictureOrDownloadIfMissing();
    }

    /**
     * Initialize the intent used for sharing images between
     * activities of different applications and set the listener
     * for the sharing menu item
     */
    // NEW!!
    private void initializeShareIntent(Bitmap bitmap) {
        shareButton.setVisible(true);

        shareButton.setOnMenuItemClickListener(menuItem -> {
            pictureUri = getPictureUri(bitmap);

            shareIntent = new Intent();
            shareIntent.setType("image/*");
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);

            startActivityForResult(Intent.createChooser(shareIntent, "Share your picture with"), SHARE_REQUEST);
            return true;
        });
    }

    /**
     * Save the bitmap in the sd local storage and return its uri
     */
    // NEW!!
    public Uri getPictureUri(Bitmap bitmap) {
        String picturePath = MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(), bitmap, "", "");
        Uri pictureUri = Uri.parse(picturePath);
        return pictureUri;
    }

    /**
     * Delete the image temporary saved in the local storage after sharing
     */
    // NEW!!
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHARE_REQUEST) {
            getActivity().getApplicationContext().getContentResolver().delete(pictureUri, null, null);
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
            MVC.controller.onPictureRequired(getActivity(), url, false);
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
            // NEW!! The bitmap has been downloaded: set its sharing intent and display it
            initializeShareIntent(bitmap);
            ((ImageView) getView().findViewById(R.id.picture)).setImageBitmap(bitmap);
            return true;
        }
        else
            return false;
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
                // NEW!! On tablet hide the share button if the titles have changed/updated
                shareButton.setVisible(false);
                break;
        }
    }
}