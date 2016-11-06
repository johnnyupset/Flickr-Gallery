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

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import it.univr.android.gallery.R;
import it.univr.android.gallery.model.Pictures;

public class PictureFragment extends Fragment {
    private final static String ARG_POSITION = "position";

    /**
     * This constructor is called when creating the view for the
     * two pane layout and when recreating the fragment upon
     * configuration change. We ensure that args exist. If they
     * already existed, previous args will be kept by the OS.
     */
    public PictureFragment() {
        init(-1);
    }

    public static PictureFragment mkInstance(int position) {
        PictureFragment fragment = new PictureFragment();
        fragment.init(position);

        return fragment;
    }

    private void init(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.picture_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        reflectState();
    }

    public void updateArticle(int position) {
        getArguments().putInt(ARG_POSITION, position);
        reflectState();
    }

    private void reflectState() {
        Bundle args = getArguments();
        int position = args.getInt(ARG_POSITION);
        if (position >= 0)
            try
            {
                // Get input stream
                InputStream image = getActivity().getAssets().open(Pictures.getFilenameFor(position));
                // Load image as Drawable
                Drawable d = Drawable.createFromStream(image, null);
                // Set image to ImageView
                ((ImageView) getView()).setImageDrawable(d);
            }
            catch(IOException ex) {}
    }
}