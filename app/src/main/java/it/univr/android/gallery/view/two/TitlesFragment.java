package it.univr.android.gallery.view.two;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * The titles fragment for a two panes layout. It modifies the standard
 * behavior by making the selected item remain highlighted.
 */
public class TitlesFragment extends it.univr.android.gallery.view.TitlesFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Make the clicked item remain visually highlighted
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Keep the selected item checked also after click
        getListView().setItemChecked(position, true);
    }
}
