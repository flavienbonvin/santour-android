package ch.hesso.santour.business;

import com.google.android.gms.maps.model.PolylineOptions;

import ch.hesso.santour.model.Position;

/**
 * Created by flavien on 11/24/17.
 */

public interface FragmentInterface {

    void updateMap(PolylineOptions polyLine, Position lastPosition);

    void setTextDistance(String text);
}
