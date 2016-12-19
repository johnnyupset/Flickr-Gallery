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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import it.univr.android.gallery.R;

public class GalleryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    public void showProgressIndicator() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator() {
        findViewById(R.id.progress).setVisibility(View.INVISIBLE);
    }
}