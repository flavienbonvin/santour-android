package ch.hesso.santour.business;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by flavien on 11/24/17.
 */

public interface FragmentInterface {

    public void setPolyLine(PolylineOptions polyLine);

    public void setTextDistance(String text);
}
