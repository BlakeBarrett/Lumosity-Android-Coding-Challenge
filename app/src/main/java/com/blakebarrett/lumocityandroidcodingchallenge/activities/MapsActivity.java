package com.blakebarrett.lumocityandroidcodingchallenge.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.blakebarrett.lumocityandroidcodingchallenge.R;
import com.blakebarrett.lumocityandroidcodingchallenge.network.PlaceFetcher;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_FINE_LOCATION = 80;
    String API_KEY;
    HashMap<Marker, Place> mMarkers = new HashMap<>();
    private LocationManager locationManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        API_KEY = getString(R.string.google_places_key);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        getCurrentLocation();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final MapsActivity self = this;
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final Place place = mMarkers.get(marker);

                if (place == null) {
                    return false;
                }

                final Intent intent = new Intent(self, PlaceInformationActivity.class);
                intent.putExtra("placeId", place.getId());
                intent.putExtra("API_KEY", API_KEY);
                startActivity(intent);

                return true;
            }
        });
//
//        final ViewStub stub = (ViewStub) findViewById(R.id.map_info_window);
//        final View view;
//        if (stub != null) {
//            view = stub.inflate();
//        } else {
//            view = new View(getBaseContext());
//        }
//
//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(final Marker marker) {
//                final Place place = mMarkers.get(marker);
//
//                if (place == null || view == null) {
//                    return view;
//                }
//
//                final TextView name = (TextView) view.findViewById(R.id.place_name);
//                final TextView address = (TextView) view.findViewById(R.id.place_address);
//                final TextView website = (TextView) view.findViewById(R.id.place_website);
//
//
//                final String placeId = place.getId();
//                final CountDownLatch latch = new CountDownLatch(1);
//                PlaceFetcher.getPlaceInfo(placeId, API_KEY, new PlaceFetcher.PlacesFetchedCompletionRunnable() {
//                    @Override
//                    public void run(final String result) {
//                        final Place updated = PlaceFetcher.placeFromPlaceInfoJson(result);
//                        name.setText(updated.getName());
//                        address.setText(updated.getAddress());
//                        website.setText(updated.getWebsiteUri().toString());
//                    }
//                });
//
//                try {
//                    latch.await();
//                } catch (final InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return view;
//            }
//
//            @Override
//            public View getInfoContents(final Marker marker) {
//                return view;
//            }
//        });
    }

    private boolean checkLocationPermissions() {
        final int accessFineLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        final int accessCoarseLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        final boolean fineLocationGranted = accessFineLocation != PackageManager.PERMISSION_GRANTED;
        final boolean coarseLocationGranted = accessCoarseLocation != PackageManager.PERMISSION_GRANTED;

        return fineLocationGranted && coarseLocationGranted;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                    // } else {
                    // screw this user.
                }

            }
        }
    }

    private void getCurrentLocation() {
        if (checkLocationPermissions()) {
            requestPermissions();
            return;
        }

        // Register the listener with the Location Manager to receive location updates
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(final Location location) {
                // Called when a new location is found by the network location provider.
                getPlaces(location);
                centerMap(location);
                // unregister for location updates -- we don't care any longer.
                try {
                    locationManager.removeUpdates(this);
                } catch (final SecurityException e) {
                    e.printStackTrace();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListener);
        } catch (final SecurityException e) {
            e.printStackTrace();
        }

    }

    private void getPlaces(final Location currentLocation) {
        PlaceFetcher.getPlaces(currentLocation, PlaceFetcher.RESTAURANT, API_KEY, new PlaceFetcher.PlacesFetchedCompletionRunnable() {
            @Override
            public void run(final String result) {
                handlePlaces(result);
            }
        });
    }

    private void handlePlaces(final String placesString) {
        final List<Place> places = PlaceFetcher.placesFromJson(placesString);
        if (places == null) {
            return;
        }
        for (int i = 0; i < places.size(); i++) {
            final Place current = places.get(i);
            addLocationToMap(current);
        }
    }

    /**
     * Adds a new point marker on teh map and centers that in view.
     *
     * @param location The Lat/Long of the new place location.
     */
    private void addLocationToMap(final Place location) {
        final LatLng latlng = location.getLatLng();
        final MarkerOptions options = new MarkerOptions();
        options.position(latlng);
        options.title((String) location.getName());
        final Marker marker = mMap.addMarker(options);
        mMarkers.put(marker, location);
    }

    private void centerMap(final Location location) {
        final LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17.0f));
    }
}
