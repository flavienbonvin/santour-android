package ch.hesso.santour.business;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.view.MainActivity;

/**
 * Created by flavien on 11/23/17.
 */

public class LocationManagement {

    private List<Position> positionsList;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private static FragmentInterface fragmentInterface;


    /**
     * Start the location tracking,
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

    /**
     * Stop the tracking
     * @param activity
     * @return positions
     */
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
                if (location != null) {
                    Position position = new Position();

                    position.setAltitude(location.getAltitude());
                    position.setLatitude(location.getLatitude());
                    position.setLongitude(location.getLongitude());
                    position.setTime(System.currentTimeMillis() / 1000);

                    callback.resolve(position);
                }
            }
        });
    }

    protected Position takePosition(Activity activity){
        return positionsList.get(positionsList.size()-1);
    }


    /**
     * Calculate the distance of the track
     * @param positions
     * @return distance
     */
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

            double temp = locationFrom.distanceTo(locationTo);
            Log.d("maxDeb", "distance temp "+temp);
            if(temp>8){
                distance += temp;
            }

            Log.d("maxDeb",distance+"");
        }

        return distance;
    }

    /**
     * Callback needed by the requestLocationUpdates method of the FusedLocationProviderClient
     * @param activity
     */
    private void callbackCreation(final Activity activity){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    cleanLocationData(location);
                    drawLine();
                }
                MainActivity.track.setDistance(calculateTrackLength(positionsList));

                if (MainActivity.track.getDistance() < 999) {
                    fragmentInterface.setTextDistance(Math.floor(MainActivity.track.getDistance()*100)/100 + " m");
                } else {
                    fragmentInterface.setTextDistance(Math.floor(MainActivity.track.getDistance()*100)/100 + " km");
                }
            }
        };
    }


    /**
     * Method used to clean the data we get from our GPS (avoid duplicate data)
     * @param location
     */
    private void cleanLocationData(Location location){
        Position newPosition = convertToPosition(location);
        if (positionsList.size() > 1) {
            Position lastPosition = positionsList.get(positionsList.size() - 1);

            if (!lastPosition.equals(newPosition)) {
                positionsList.add(newPosition);
            }
        } else {
            positionsList.add(newPosition);
        }
    }

    /**
     * Draw a line on the map with all the last position logged
     */
    private void drawLine(){
        LatLng latLng;
        PolylineOptions polylineOptions = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);

        for (Position position : positionsList) {
            latLng = new LatLng(position.latitude, position.longitude);
            polylineOptions.add(latLng);
        }
        fragmentInterface.setPolyLine(polylineOptions);
    }

    /**
     * Convert a Location object to a Position object
     * @param location
     * @return position
     */
    private Position convertToPosition(Location location){
        Position position = new Position();

        position.setAltitude(location.getAltitude());
        position.setLatitude(location.getLatitude());
        position.setLongitude(location.getLongitude());
        position.setTime(System.currentTimeMillis()/1000);

        return position;
    }

    /**
     * To be able to change items on the view we have to set an interface to access some methods
     * @param fragmentInterface
     */
    public static void interfaceToWatch(FragmentInterface fragmentInterface){
        LocationManagement.fragmentInterface = fragmentInterface;
    }
}