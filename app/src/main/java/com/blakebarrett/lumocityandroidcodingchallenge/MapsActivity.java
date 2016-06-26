package com.blakebarrett.lumocityandroidcodingchallenge;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_FINE_LOCATION = 80;

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
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                getPlaces(location);
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
        final String key = getString(R.string.google_maps_key);
        PlaceFetcher.getPlaces(currentLocation, "restaurant", key, new PlaceFetcher.PlacesFetchedCompletionRunnable(){
            @Override
            public void run(final String result) {
                /*
                    {
                       "error_message" : "This IP, site or mobile application is not authorized to use this API key. Request received from IP address 69.181.195.233, with empty referer",
                       "html_attributions" : [],
                       "results" : [],
                       "status" : "REQUEST_DENIED"
                    }
                 */
                handlePlaces(result);
            }
        });
    }

    private void handlePlaces(final String places) {

    }

    /**
     * Adds a new point marker on teh map and centers that in view.
     *
     * @param location The Lat/Long of the new place location.
     */
    private void addLocationToMap(final Location location) {
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latlng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }

}
