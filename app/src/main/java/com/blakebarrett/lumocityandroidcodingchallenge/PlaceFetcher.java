package com.blakebarrett.lumocityandroidcodingchallenge;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * PlaceFetcher fetches places using Google's radarsearch API.
 *
 * Created by Blake on 6/25/16.
 */
public class PlaceFetcher {

    public static void getPlaces(final Location origin, final String placeType, final String KEY,
                                 final PlacesFetchedCompletionRunnable completionRunnable) {
        // TODO: Build this request
        // https://maps.googleapis.com/maps/api/place/radarsearch/json?location=51.503186,-0.126446&radius=5000&type=museum&key=YOUR_API_KEY

        final StringBuffer buffer = new StringBuffer("https://maps.googleapis.com/maps/api/place/radarsearch/json?location=");
        buffer.append(String.valueOf(origin.getLatitude()));
        buffer.append(",");
        buffer.append(String.valueOf(origin.getLongitude()));
        buffer.append("&type=").append(placeType);
        buffer.append("&key=").append(KEY);

        // https://maps.googleapis.com/maps/api/place/radarsearch/json?location=37.7591103,-122.5087203&type=restaurant&key=AIzaSyAS00GUmuv4Z_oWFe1yJq1gu1cIr5ycElU
        final String urlString = buffer.toString();

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                final InputStream stream;
                try {
                    stream = new URL(urlString).openStream();
                    return inputStreamToString(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(final String result) {
                if (completionRunnable != null) {
                    completionRunnable.run(result);
                }
            }
        }.execute(null, null, null);

    }

    private static String inputStreamToString(final InputStream inputStream) {
        final String response;
        try {
            // Read the response (if anything).
            response = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
            inputStream.close();
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public static List<Place> fromJson(final String jsonString) {

        try {
            final JSONObject object = new JSONObject(jsonString);
            final JSONArray results = object.optJSONArray("results");
            return PlaceParser.parsePlaces(results);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface PlacesFetchedCompletionRunnable {
        void run(final String result);
    }
}
