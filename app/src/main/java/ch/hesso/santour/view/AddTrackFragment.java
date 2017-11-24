package ch.hesso.santour.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ch.hesso.santour.R;
import ch.hesso.santour.business.LocationManagement;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.POI;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;


public class AddTrackFragment extends Fragment implements OnMapReadyCallback{

    //Google Map
    private MapView mapView;
    private GoogleMap map;

    public AddTrackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_track, container, false);

        mapView = rootView.findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        //button login
        Button btnSave = rootView.findViewById(R.id.add_track_save_button);
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                EditText nameTrack  = (EditText)rootView.findViewById(R.id.nameTrack);
                String name = nameTrack.getText().toString();
                if(name.equals("")){
                    return;
                }

                MainActivity.track = new Track();
                MainActivity.track.setName(name);
                Intent intent = new Intent(rootView.getContext(), TrackActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng coordinate = new LatLng(86, 20);
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        LocationManagement.getCurrentPosition(AddTrackFragment.this.getActivity(), new DBCallback() {
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


}
