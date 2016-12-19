/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.univr.android.gallery.view;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.univr.android.gallery.R;
import it.univr.android.gallery.controller.Controller;
import it.univr.android.gallery.model.Pictures;

import static it.univr.android.gallery.model.Pictures.Event.PICTURES_LIST_CHANGED;

public class TitlesFragment extends ListFragment implements GalleryFragment {

    private GalleryLayout getGalleryLayout() {
        return ((GalleryLayout) getActivity().findViewById(R.id.gallery_layout_container));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getListAdapter() == null)
            setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, new String[0]));

        onModelChanged(PICTURES_LIST_CHANGED);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_titles, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent layout of selected item
        getGalleryLayout().onTitleSelected(position);
        // Keep the selected item checked also after click
        getListView().setItemChecked(position, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_load) {
            ((GalleryActivity) getActivity()).showProgressIndicator();
            Controller.fetchListOfPictures(getActivity(), 40);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    public void onModelChanged(Pictures.Event event) {
        if (event == PICTURES_LIST_CHANGED) {
            String[] titles = Pictures.get().getTitles();
            if (titles != null)
                // Create an array adapter for the list view, using the Pictures titles array
                setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, titles));
            else {
                ((GalleryActivity) getActivity()).showProgressIndicator();
                Controller.fetchListOfPictures(getActivity(), 40);
            }
        }
    }
}