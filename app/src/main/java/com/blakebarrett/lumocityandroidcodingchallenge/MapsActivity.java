package com.blakebarrett.lumocityandroidcodingchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_PLACE_PICKER = 97;

    private LocationManager locationManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mMap = googleMap;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            final PlaceFilter placeFilter = new PlaceFilter();
            placeFilter.getPlaceIds();

            mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title((String) place.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkLocationPermissions() {
        final int accessFineLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        final int accessCoarseLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        return accessFineLocation != PackageManager.PERMISSION_GRANTED &&
                accessCoarseLocation != PackageManager.PERMISSION_GRANTED;
    }

    // TODO: Build this: https://developer.android.com/training/location/retrieve-current.html
    private void getCurrentLocation() {

        // Register the listener with the Location Manager to receive location updates
        if (checkLocationPermissions()) {
            return;
        }

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                getPlaces(location);

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

    // TODO: Make this request: https://maps.googleapis.com/maps/api/place/radarsearch/json?location=51.503186,-0.126446&radius=5000&type=museum&key=YOUR_API_KEY
    private void getPlaces(final Location currentLocation) {
        // https://maps.googleapis.com/maps/api/place/radarsearch/output?type=restaurant&key=AIzaSyAS00GUmuv4Z_oWFe1yJq1gu1cIr5ycElU
        final String key = getString(R.string.google_maps_key);
        PlaceFetcher.getPlaces(currentLocation, "restaurant", key, new PlaceFetcher.PlacesFetchedCompletionRunnable(){
            @Override
            public void run(final String result) {
                handlePlaces(result);
            }
        });
    }

    private void handlePlaces(final String places) {

    }

}
