package it.univr.android.gallery.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import it.univr.android.gallery.R;
import it.univr.android.gallery.model.Pictures;

public class TwoPanesGalleryLayout extends LinearLayout implements GalleryLayout {

    private TitlesFragment getTitlesFragment() {
        return (TitlesFragment) ((Activity) getContext()).getFragmentManager().findFragmentById(R.id.titles_fragment);
    }

    private PictureFragment getPictureFragment() {
        return (PictureFragment) ((Activity) getContext()).getFragmentManager().findFragmentById(R.id.picture_fragment);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Pictures.get().registerView(this);

        // Make the clicked item remain visually highlighted
        getTitlesFragment().getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    protected void onDetachedFromWindow() {
        Pictures.get().unregisterView(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onTitleSelected(int position) {
        getPictureFragment().updateArticle(position);
    }

    @Override
    public void onModelChanged(Pictures.Event event) {
        getTitlesFragment().onModelChanged(event);
        getPictureFragment().onModelChanged(event);
    }

    public TwoPanesGalleryLayout(Context context) {
        super(context);
    }
    public TwoPanesGalleryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
