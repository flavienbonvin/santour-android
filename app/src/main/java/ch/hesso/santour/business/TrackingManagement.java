package ch.hesso.santour.business;

import android.app.Activity;

import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;

/**
 * Created by flavien on 11/23/17.
 */

public class TrackingManagement {


    /**
     * Method used to start a track
     * @param activity
     */
    public static void startTracking(Activity activity){

        Track track = new Track();
        List<Position> positions = new ArrayList<>();

        LocationManagement.startLocationTracking(activity);
    }

    public static void stopTracking(Activity activity){

    }

    public static void addPOD(Activity activity){

    }

    public static void addPOI(Activity activity){

    }


}
