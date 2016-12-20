package it.univr.android.gallery.view.two;

import android.os.Bundle;
import android.widget.ListView;

public class TitlesFragment extends it.univr.android.gallery.view.TitlesFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Make the clicked item remain visually highlighted
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
