package com.hotmoka.android.gallery.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hotmoka.android.gallery.R;

/**
 * The user interface of the app. It uses two
 * versions of the XML layout specification, automatically
 * selected by the Android framework, for phones or tablets.
 * These layouts use distinct implementations of the
 * layout classes.
 */
public class GalleryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    /**
     * Makes the progress indicator visible.
     */
    public void showProgressIndicator() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
    }

    /**
     * Makes the progress indicator invisible.
     */
    public void hideProgressIndicator() {
        findViewById(R.id.progress).setVisibility(View.INVISIBLE);
    }
}