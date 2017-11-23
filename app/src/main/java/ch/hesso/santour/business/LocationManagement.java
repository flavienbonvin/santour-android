package ch.hesso.santour.business;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Created by flavien on 11/23/17.
 */

public class LocationManagement {

    private static FusedLocationProviderClient fusedLocationProviderClient;
    private static LocationRequest locationRequest;
    private static LocationCallback locationCallback;

    /**
     *
     * @param activity
     */
    protected static void startLocationTracking(Activity activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000).setFastestInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        callbackCreation(activity);

        PermissionManagement.checkMandatoryPermission(activity);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }


    public static void stopTracking(Activity activity){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private static void callbackCreation(final Activity activity){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.d("Location update", location.getAltitude() + " " + location.getLatitude() + " " + location.getLongitude());
                    Toast.makeText(activity, "Location update", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
