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
package it.univr.android.news;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, Ipsum.Headlines));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent layout of selected item
        ((NewsLayout) getActivity().findViewById(R.id.news_layout_container)).onArticleSelected(position);
        // Keep the selected item checked also after click
        getListView().setItemChecked(position, true);
    }
}