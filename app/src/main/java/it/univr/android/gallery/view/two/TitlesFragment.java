package it.univr.android.gallery.view.two;

import android.os.Bundle;
import android.widget.ListView;

public class TitlesFragment extends it.univr.android.gallery.view.single.TitlesFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the clicked item remain visually highlighted
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
