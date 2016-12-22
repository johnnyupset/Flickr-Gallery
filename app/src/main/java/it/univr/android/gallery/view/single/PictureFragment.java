package it.univr.android.gallery.view.single;

import android.widget.TextView;

import it.univr.android.gallery.MVC;
import it.univr.android.gallery.R;

public class PictureFragment extends it.univr.android.gallery.view.PictureFragment {

    public static PictureFragment mkInstance(int position) {
        PictureFragment fragment = new PictureFragment();
        fragment.init(position);

        return fragment;
    }

    protected boolean showBitmapIfDownloaded(int position) {
        boolean shown = super.showBitmapIfDownloaded(position);
        if (shown)
            ((TextView) getView().findViewById(R.id.picture_title)).setText(MVC.model.getTitles()[position]);

        return shown;
    }
}
