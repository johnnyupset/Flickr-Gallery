package it.univr.android.gallery.view.single;

import android.widget.TextView;

import it.univr.android.gallery.R;
import it.univr.android.gallery.model.Pictures;

public class PictureFragment extends it.univr.android.gallery.view.PictureFragment {

    public static PictureFragment mkInstance(int position) {
        PictureFragment fragment = new PictureFragment();
        fragment.init(position);

        return fragment;
    }

    protected boolean reflectPosition(int position) {
        boolean reflected = super.reflectPosition(position);
        if (reflected)
            ((TextView) getView().findViewById(R.id.picture_title)).setText(Pictures.get().getTitles()[position]);

        return reflected;
    }
}
