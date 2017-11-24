package ch.hesso.santour.business;

import android.app.Activity;
import android.app.Fragment;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.R;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.view.MainActivity;
import ch.hesso.santour.view.TrackFragment;

/**
 * Created by flavien on 11/23/17.
 */

public class LocationManagement {

    private List<Position> positionsList;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private static Fragment watchedFragment;


    /**
     *
     * @param activity
     */
    protected void startLocationTracking(Activity activity) {
        positionsList = new ArrayList<>();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000).setFastestInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        callbackCreation(activity);

        PermissionManagement.checkMandatoryPermission(activity);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    protected Position takePosition(Activity activity){
        return positionsList.get(positionsList.size()-1);
    }


    protected List<Position> stopTracking(Activity activity){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        return positionsList;
    }

    public static void getCurrentPosition(Activity activity, final DBCallback callback){
        FusedLocationProviderClient fusedTemp = LocationServices.getFusedLocationProviderClient(activity);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(0).setFastestInterval(0).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        PermissionManagement.checkMandatoryPermission(activity);
        fusedTemp.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Position position = new Position();

                position.setAltitude(location.getAltitude());
                position.setLatitude(location.getLatitude());
                position.setLongitude(location.getLongitude());
                position.setTime(System.currentTimeMillis()/1000);

                callback.resolve(position);
            }
        });
    }

    protected double calculateTrackLength(List<Position> positions){
        Location locationFrom = new Location("temp");
        Location locationTo = new Location("temp");
        double distance = 0;

        for (int i=0; i < positions.size()-1; i++){
            locationFrom.setLatitude(positions.get(i).latitude);
            locationFrom.setLongitude(positions.get(i).longitude);
            locationFrom.setAltitude(positions.get(i).altitude);

            locationTo.setLatitude(positions.get(i+1).latitude);
            locationTo.setLongitude(positions.get(i+1).longitude);
            locationTo.setAltitude(positions.get(i+1).altitude);

            distance += locationFrom.distanceTo(locationTo);
        }

        return distance;
    }

    private void callbackCreation(final Activity activity){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    cleanLocationData(location);
                }
                MainActivity.track.setDistance(calculateTrackLength(positionsList));
                TextView text = (TextView) watchedFragment.getActivity().findViewById(R.id.tv_distance);

                if (MainActivity.track.getDistance() < 999) {
                    text.setText(Math.floor(MainActivity.track.getDistance()*100)/100 + " m");
                } else {
                    text.setText(Math.floor(MainActivity.track.getDistance()/10)/100 + " km");
                }
            }
        };
    }


    public static void FragmentToWatch(Fragment fragment){
        watchedFragment = fragment;
    }

    private Position convertToPosition(Location location){
        Position position = new Position();

        position.setAltitude(location.getAltitude());
        position.setLatitude(location.getLatitude()/100);
        position.setLongitude(location.getLongitude()/100);
        position.setTime(System.currentTimeMillis()/1000);

        return position;
    }

    private void cleanLocationData(Location location){
        Position lastPosition = positionsList.get(positionsList.size()-1);
        Position newPosition = convertToPosition(location);

        if(!lastPosition.equals(newPosition)){
            positionsList.add(newPosition);
        }
    }
}