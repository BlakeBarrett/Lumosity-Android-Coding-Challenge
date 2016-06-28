package com.blakebarrett.lumocityandroidcodingchallenge.network;

import android.location.Location;
import android.os.AsyncTask;

import com.blakebarrett.lumocityandroidcodingchallenge.factories.PlaceParser;
import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * PlaceFetcher fetches places using Google's radarsearch API.
 *
 * Created by Blake on 6/25/16.
 */
public class PlaceFetcher {

    public static final String RESTAURANT = "restaurant";

    public static void getPlaceInfo(final String placeId, final String KEY,
                                    final PlacesFetchedCompletionRunnable completionRunnable) {

        final StringBuffer buffer = new StringBuffer("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        buffer.append(placeId);
        buffer.append("&key=").append(KEY);

        // https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&key=AIzaSyC3nvZJCE0sr-YNs_U3p2_AiAXddCZwq14
        final String urlString = buffer.toString();
        doGetRequest(urlString, completionRunnable);
    }

    public static void getPlaces(final Location origin, final String placeType, final String KEY,
                                 final PlacesFetchedCompletionRunnable completionRunnable) {

        final StringBuffer buffer = new StringBuffer("https://maps.googleapis.com/maps/api/place/radarsearch/json?radius=500&location=");
        buffer.append(String.valueOf(origin.getLatitude()));
        buffer.append(",");
        buffer.append(String.valueOf(origin.getLongitude()));
        buffer.append("&type=").append(placeType);
        buffer.append("&key=").append(KEY);

        // https://maps.googleapis.com/maps/api/place/radarsearch/json?location=37.7591103,-122.5087203&type=restaurant&key=AIzaSyAS00GUmuv4Z_oWFe1yJq1gu1cIr5ycElU
        final String urlString = buffer.toString();
        doGetRequest(urlString, completionRunnable);
    }

    private static void doGetRequest(final String urlString, final PlacesFetchedCompletionRunnable completionRunnable) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {


                try {
                    final HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
                    final InputStream stream = new BufferedInputStream(connection.getInputStream());
                    final String result = inputStreamToString(stream);
                    return result;
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

    public static List<Place> placesFromJson(final String jsonString) {

        try {
            final JSONObject object = new JSONObject(jsonString);
            final JSONArray results = object.optJSONArray("results");
            return PlaceParser.parsePlaces(results);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Place placeFromPlaceInfoJson(final String jsonString) {
        try {
            final JSONObject results = new JSONObject(jsonString).optJSONObject("result");
            return PlaceParser.parsePlace(results);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface PlacesFetchedCompletionRunnable {
        void run(final String result);
    }
}
