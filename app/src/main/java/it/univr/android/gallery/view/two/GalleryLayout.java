package it.univr.android.gallery.view.two;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import it.univr.android.gallery.MVC;
import it.univr.android.gallery.R;
import it.univr.android.gallery.model.Pictures;
import it.univr.android.gallery.view.GalleryActivity;

public class GalleryLayout extends LinearLayout implements it.univr.android.gallery.view.GalleryLayout {

    private TitlesFragment getTitlesFragment() {
        return (TitlesFragment) ((Activity) getContext()).getFragmentManager().findFragmentById(R.id.titles_fragment);
    }

    private PictureFragment getPictureFragment() {
        return (PictureFragment) ((Activity) getContext()).getFragmentManager().findFragmentById(R.id.picture_fragment);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MVC.registerView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        MVC.unregisterView(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onTitleSelected(int position) {
        getPictureFragment().updatePicture(position);
    }

    @Override
    public void onModelChanged(Pictures.Event event) {
        getTitlesFragment().onModelChanged(event);
        getPictureFragment().onModelChanged(event);
        if (MVC.controller.isIdle())
            ((GalleryActivity) getContext()).hideProgressIndicator();
    }

    public GalleryLayout(Context context) {
        super(context);
    }
    public GalleryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
