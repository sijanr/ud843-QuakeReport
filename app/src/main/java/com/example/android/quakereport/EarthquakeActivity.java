/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.example.android.quakereport;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private final String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private RecyclerView mRecyclerView;
    private EarthquakeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        new EarthquakeAsyncTask().execute(urlString);

    }

    private class EarthquakeAsyncTask extends AsyncTask<String,Void,List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... strings) {
            String url = strings[0];
            return QueryUtils.fetchEarthquakeDate(url);
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakeList) {
            // Find a reference to the {@link RecyclerView} in the layout
            mRecyclerView = findViewById(R.id.recycler_view);

            // Create a new adapter of earthquakes
            mAdapter = new EarthquakeListAdapter(EarthquakeActivity.this, earthquakeList);

            // Set the adapter on the RecyclerView
            // so the list can be populated in the user interface
            mRecyclerView.setAdapter(mAdapter);

            //give the RecyclerView a default layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(EarthquakeActivity.this));
        }
    }


}
