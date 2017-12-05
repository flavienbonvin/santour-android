package ch.hesso.santour.business;

import com.google.android.gms.maps.model.PolylineOptions;

import ch.hesso.santour.model.Position;

/**
 * Created by flavien on 11/24/17.
 */

public interface FragmentInterface {

    public void updateMap(PolylineOptions polyLine, Position lastPosition);

    public void setTextDistance(String text);
}
