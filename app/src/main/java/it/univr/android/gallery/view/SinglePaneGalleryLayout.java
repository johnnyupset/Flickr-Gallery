package it.univr.android.gallery.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import it.univr.android.gallery.R;
import it.univr.android.gallery.controller.Controller;
import it.univr.android.gallery.model.Pictures;

public class SinglePaneGalleryLayout extends FrameLayout implements GalleryLayout {

    private GalleryFragment getFragment() {
        return (GalleryFragment) getFragmentManager().findFragmentById(R.id.gallery_layout_container);
    }

    private FragmentManager getFragmentManager() {
        return ((Activity) getContext()).getFragmentManager();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Pictures.get().registerView(this);

        // Show the titles fragment at start
        if (getFragment() == null)
            getFragmentManager().beginTransaction()
                    .add(R.id.gallery_layout_container, new TitlesFragment()).commit();
    }

    @Override
    protected void onDetachedFromWindow() {
        Pictures.get().unregisterView(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onTitleSelected(int position) {
        // Create fragment and give it an argument for the selected picture
        getFragmentManager().beginTransaction()
            // Replace whatever is in the gallery_layout_container view with this fragment
            .replace(R.id.gallery_layout_container, PictureFragment.mkInstance(position))
            // Add the transaction to the back stack so the user can navigate back
            .addToBackStack(null)
            // Commit the transaction
            .commit();
    }

    @Override
    public void onModelChanged(Pictures.Event event) {
        getFragment().onModelChanged(event);
        if (Controller.isIdle())
            ((GalleryActivity) getContext()).hideProgressIndicator();
    }

    public SinglePaneGalleryLayout(Context context) {
        super(context);
    }
    public SinglePaneGalleryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}