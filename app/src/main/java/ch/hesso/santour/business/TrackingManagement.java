package ch.hesso.santour.business;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Main.MainActivity;

/**
 * Created by flavien on 11/23/17.
 */

public class TrackingManagement {

    private static LocationManagement locationManagement = new LocationManagement();

    /**
     * Method used to start a track
     * @param activity
     */
    public static void startTracking(final Activity activity){

        Track track = new Track();
        List<Position> positions = new ArrayList<>();



        locationManagement.startLocationTracking(activity);
    }

    /**
     * Stop the tracking of the user (end of the track)
     * @param activity
     */
    public static void stopTracking(Activity activity){
        List<Position> positionList = locationManagement.stopTracking(activity);
        MainActivity.track.setPositions(positionList);
    }

    public static void resumeTracking(Activity activity){
        locationManagement.resumeTracking(activity);
    }

    public static void pauseTracking(Activity activity){
        locationManagement.stopTracking(activity);
    }

    public static void addPOD(Activity activity){
        
    }

    public static void addPOI(Activity activity){

    }


}
