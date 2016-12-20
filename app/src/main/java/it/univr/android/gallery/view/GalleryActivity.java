package it.univr.android.gallery.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import it.univr.android.gallery.R;

public class GalleryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    public void showProgressIndicator() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator() {
        findViewById(R.id.progress).setVisibility(View.INVISIBLE);
    }
}