package com.blakebarrett.lumocityandroidcodingchallenge;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Blake on 6/25/16.
 */
public class PlaceFetcher {

    public interface PlacesFetchedCompletionRunnable {
        public void run(final String result);
    }

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

}
