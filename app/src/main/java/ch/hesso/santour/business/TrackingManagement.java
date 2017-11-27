package ch.hesso.santour.business;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.R;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.MainActivity;

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

    public static void stopTracking(Activity activity){
        List<Position> positionList = locationManagement.stopTracking(activity);
        MainActivity.track.setPositions(positionList);
        TrackDB.add(MainActivity.track);
    }

    public static void addPOD(Activity activity){
        
    }

    public static void addPOI(Activity activity){

    }


}
