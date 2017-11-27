package ch.hesso.santour.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;

import com.google.android.gms.maps.model.MapStyleOptions;

import ch.hesso.santour.R;
import ch.hesso.santour.business.FragmentInterface;
import ch.hesso.santour.business.LocationManagement;
import ch.hesso.santour.business.TrackingManagement;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.Position;

public class TrackFragment extends Fragment implements OnMapReadyCallback, FragmentInterface {

    //Play & Stop button
    private ImageButton trackPlayButton;
    private ImageButton trackStopButton;
    private CardView cardViewRecord;

    //Add POI / POD button
    private ImageButton addPOIButton;
    private ImageButton addPODButton;

    //Chronometer
    private Chronometer chrono;

    //Google Map
    private MapView mapView;
    private GoogleMap map;

    //Fragment
    private Fragment fragment;
    private FragmentManager fragmentManager;

    //Bottom Navigation Bar
    private BottomNavigationView navigation;

    private Marker marker;


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
        setHasOptionsMenu(true);
        mapView = rootView.findViewById(R.id.track_map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        trackPlayButton = rootView.findViewById(R.id.track_play_button);
        trackStopButton = rootView.findViewById(R.id.track_stop_button);
        cardViewRecord = rootView.findViewById(R.id.track_card_view_record);

        chrono = rootView.findViewById(R.id.track_chronometer);
        trackPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackPlayButton.setVisibility(View.GONE);
                trackStopButton.setVisibility(View.VISIBLE);
                cardViewRecord.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryRed));
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                TrackingManagement.startTracking(TrackFragment.this.getActivity());
            }
        });

        trackStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackPlayButton.setVisibility(View.VISIBLE);
                trackStopButton.setVisibility(View.GONE);
                cardViewRecord.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                chrono.stop();
                TrackingManagement.stopTracking(TrackFragment.this.getActivity());
            }
        });

        LocationManagement.interfaceToWatch(TrackFragment.this);


        addPOIButton = rootView.findViewById(R.id.track_add_poi_button);
        addPOIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager  = getFragmentManager();
                fragment  = new TrackAddPOIFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_content, fragment).commit();
            }
        });

        addPODButton = rootView.findViewById(R.id.track_add_pod_button);
        addPODButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager  = getFragmentManager();
                fragment  = new TrackAddPODFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_content, fragment).commit();
            }
        });



        return  rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_json));
        map = googleMap;

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setAllGesturesEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);

        LatLng coordinate = new LatLng(86, 20);
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        LocationManagement.getCurrentPosition(TrackFragment.this.getActivity(), new DBCallback() {
            @Override
            public void resolve(Object o) {
                Position p = (Position)o;
                LatLng latlng =new LatLng(p.latitude,p.longitude);

                map.addMarker(new MarkerOptions().position(latlng));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,18));
            }
        });
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

    @Override
    public void updateMap(PolylineOptions polyLine, Position position) {
        map.clear();
        map.addPolyline(polyLine);

        LatLng latLng = new LatLng(position.latitude, position.longitude);
        float[] hsv = new float[3];
        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
    }

    @Override
    public void setTextDistance(String text) {
        TextView textView = (TextView) getActivity().findViewById(R.id.tv_distance);
        textView.setText(text);
    }
}
