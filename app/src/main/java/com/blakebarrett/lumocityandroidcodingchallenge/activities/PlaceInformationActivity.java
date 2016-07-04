package com.blakebarrett.lumocityandroidcodingchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blakebarrett.lumocityandroidcodingchallenge.R;
import com.blakebarrett.lumocityandroidcodingchallenge.network.PlaceFetcher;
import com.google.android.gms.location.places.Place;

public class PlaceInformationActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_information);

        this.name = (TextView) findViewById(R.id.place_name);
        this.address = (TextView) findViewById(R.id.place_address);
        this.website = (TextView) findViewById(R.id.place_website);

        final Intent intent = getIntent();

        final String placeId = intent.getStringExtra("placeId");
        final String API_KEY = intent.getStringExtra("API_KEY");

        fetchPlace(placeId, API_KEY);
    }

    void fetchPlace(final String placeId, final String API_KEY) {
        PlaceFetcher.getPlaceInfo(placeId, API_KEY, new PlaceFetcher.PlacesFetchedCompletionRunnable() {
            @Override
            public void run(final String result) {
                final Place updated = PlaceFetcher.placeFromPlaceInfoJson(result);
                name.setText(updated.getName());
                address.setText(updated.getAddress());
                website.setText(updated.getWebsiteUri().toString());
            }
        });
    }
}
