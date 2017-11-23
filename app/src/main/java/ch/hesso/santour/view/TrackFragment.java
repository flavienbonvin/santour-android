package ch.hesso.santour.view;

import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import ch.hesso.santour.R;

public class TrackFragment extends Fragment implements OnMapReadyCallback {

    //Play & Stop button
    private ImageButton trackPlayButton;
    private ImageButton trackStopButton;

    //Chronometer
    private Chronometer chrono;

    //Google Map
    private MapView mapView;
    private GoogleMap map;


    public TrackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        trackPlayButton = rootView.findViewById(R.id.track_play_button);
        chrono = rootView.findViewById(R.id.track_chronometer);
        trackPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackPlayButton.setVisibility(View.GONE);
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
            }
        });

        trackStopButton = rootView.findViewById(R.id.track_stop_button);
        trackStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackPlayButton.setVisibility(View.VISIBLE);
                chrono.stop();
            }
        });
        return  rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng coordinate = new LatLng(86, 20);
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinate));

    }





    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
