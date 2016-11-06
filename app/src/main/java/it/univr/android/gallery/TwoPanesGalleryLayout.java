package it.univr.android.gallery;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TwoPanesGalleryLayout extends LinearLayout implements GalleryLayout {

    private FragmentManager getFragmentManager() {
        return ((Activity) getContext()).getFragmentManager();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Make the clicked item remain visually highlighted
        ((ListFragment) getFragmentManager().findFragmentById(R.id.titles_fragment))
            .getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onTitleSelected(int position) {
        // Capture the picture fragment from the activity layout
        ((PictureFragment) getFragmentManager().findFragmentById(R.id.picture_fragment))
            .updateArticle(position);
    }

    public TwoPanesGalleryLayout(Context context) {
        super(context);
    }
    public TwoPanesGalleryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
