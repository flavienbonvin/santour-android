package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import ch.hesso.santour.business.LocationManagement;

/**
 * Created by Maxime on 18.11.2017.
 */

public class Position {
    public double latitude;
    public double longitude;
    public double altitude;
    public double time;

    public Position() {
    }

    public Position(double latitude, double longitude, double altitude, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.time = time;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Position position = (Position) obj;

        if (position.latitude != this.latitude){
            return false;
        }
        if(position.longitude != this.longitude){
            return false;
        }
        return true;
    }
}
