package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import ch.hesso.santour.R;
import ch.hesso.santour.business.FragmentInterface;
import ch.hesso.santour.business.LocationManagement;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Main.MainActivity;
import ch.hesso.santour.view.Tracking.Fragment.FragmentRecording;

/**
 * Created by flavien on 12/4/17.
 */

public class FragmentDetailsTrack extends Fragment implements OnMapReadyCallback {

    private Track detailedTrack;
    private View rootView;

    //Google Map
    private MapView mapView;
    private GoogleMap map;

    public FragmentDetailsTrack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_details_track, container, false);

        //detailedTrack = (Track)getArguments().getSerializable("track");
        //detailedTrack = (Track)getActivity().getIntent().getSerializableExtra("track");

        detailedTrack = MainActivity.track;

        mapView = rootView.findViewById(R.id.edit_track_map_mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        EditText editText = (EditText)rootView.findViewById(R.id.edit_track_textView_nameTrack);
        editText.setText(detailedTrack.getName());


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_json));
        map = googleMap;

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(false);

       PolylineOptions polylineOptions = new PolylineOptions().width(7).color(Color.parseColor("#52c7b8")).geodesic(true);

        LatLng coordinate = new LatLng(detailedTrack.getPositions().get(0).latitude,detailedTrack.getPositions().get(0).longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,18));

        for (Position position : detailedTrack.getPositions()) {
            LatLng latLng = new LatLng(position.latitude, position.longitude);
            polylineOptions.add(latLng);
        }

        map.clear();
        map.addPolyline(polylineOptions);
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
