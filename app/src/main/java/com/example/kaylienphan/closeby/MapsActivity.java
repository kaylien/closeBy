package com.example.kaylienphan.closeby;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

   // private
   private FusedLocationProviderClient mFusedLocationClient;
    private int locationRequestCode = 1000;

    private double wayLatitude = 0.0, wayLongitude = 0.0;
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//    private android.widget.Button btnLocation;
//    private TextView txtLocation;
//    private android.widget.Button btnContinueLocation;
//    private TextView txtContinueLocation;
//    private StringBuilder stringBuilder;

    private boolean isContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Will be used to retrieve the last known location of the user
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //TODO: Decide if you really want to set the Location Request right here. Will it run onPause()?
        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequestHighAccuracy);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        //TODO: When you initially give permission to enable location tracking,
        // TODO: it doesn't continuously give the location because this addOnCompleteListener is in onCreate

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    Log.d("response var", response.toString());

                    Log.d("we in this one", "5");


                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        MapsActivity.this, locationRequestCode);
                                Log.d("we in this one", "1");
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                                Log.d("we in this one", "2");
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.

                                Log.d("we in this one", "3");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            Log.d("addOnCompleteListener", "SETTINGS CHANGE UNAVAILABLE");

                            Log.d("we in this one", "4");
                            break;
                    }
                }
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(MapsActivity.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }**/
}
