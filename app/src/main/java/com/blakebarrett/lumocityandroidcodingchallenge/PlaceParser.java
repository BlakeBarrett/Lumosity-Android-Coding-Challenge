package com.blakebarrett.lumocityandroidcodingchallenge;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Blake on 6/26/16.
 */
public class PlaceParser {
    public static List<Place> parsePlaces(final JSONArray placesJsonArray) {
        final List<Place> places = new ArrayList<>();

        for (int i = 0; i < placesJsonArray.length(); i++) {
            final JSONObject currentPlaceJson = placesJsonArray.optJSONObject(i);
            final Place place = new Place() {
                @Override
                public String getId() {
                    return currentPlaceJson.optString("id");
                }

                @Override
                public List<Integer> getPlaceTypes() {
                    return null;
                }

                @Override
                public CharSequence getAddress() {
                    return null;
                }

                @Override
                public Locale getLocale() {
                    return null;
                }

                @Override
                public CharSequence getName() {
                    return currentPlaceJson.optString("name");
                }

                @Override
                public LatLng getLatLng() {
                    double lat = currentPlaceJson.optJSONObject("geometry").optJSONObject("location").optDouble("lat");
                    double lon = currentPlaceJson.optJSONObject("geometry").optJSONObject("location").optDouble("lng");
                    return new LatLng(lat, lon);
                }

                @Override
                public LatLngBounds getViewport() {
                    return null;
                }

                @Override
                public Uri getWebsiteUri() {
                    return null;
                }

                @Override
                public CharSequence getPhoneNumber() {
                    return null;
                }

                @Override
                public float getRating() {
                    return 0;
                }

                @Override
                public int getPriceLevel() {
                    return 0;
                }

                @Override
                public CharSequence getAttributions() {
                    return null;
                }

                @Override
                public Place freeze() {
                    return null;
                }

                @Override
                public boolean isDataValid() {
                    return false;
                }
            };

            places.add(place);
        }

        return places;
    }
}
