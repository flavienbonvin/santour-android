package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import ch.hesso.santour.R;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.view.Edition.Activity.TrackEditActivity;

/**
 * Created by flavien on 12/4/17.
 */

public class FragmentDetailsTrack extends Fragment implements OnMapReadyCallback {

    private View rootView;

    //Google Map
    private MapView mapView;

    private EditText editTextName;
    private SeekBar seekBarDifficulty;
    private TextView textViewDistance;


    public FragmentDetailsTrack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_details_track, container, false);


        initFields();

        mapView = rootView.findViewById(R.id.edit_track_map_mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;
    }

    /**
     * Draw the line of the track on the map
     * @param googleMap
     * @throws SecurityException
     */
    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_json));
        GoogleMap map = googleMap;

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false);

        PolylineOptions polylineOptions = new PolylineOptions().width(7).color(Color.parseColor("#52c7b8")).geodesic(true);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        for (Position position : TrackEditActivity.trackDetails.getPositions()) {
            LatLng latLng = new LatLng(position.latitude, position.longitude);
            polylineOptions.add(latLng);
            builder.include(latLng);
        }

        LatLngBounds bounds = builder.build();

        map.clear();
        map.addPolyline(polylineOptions);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        LatLng startLatLng = new LatLng(TrackEditActivity.trackDetails.getPositions().get(0).latitude, TrackEditActivity.trackDetails.getPositions().get(0).longitude);
        map.addMarker(new MarkerOptions()
            .position(startLatLng)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng endLatLng = new LatLng(TrackEditActivity.trackDetails.getPositions().get(TrackEditActivity.trackDetails.getPositions().size()-1).latitude, TrackEditActivity.trackDetails.getPositions().get(TrackEditActivity.trackDetails.getPositions().size()-1).longitude);
        map.addMarker(new MarkerOptions()
                .position(endLatLng));
    }

    private void initFields(){
        editTextName  = rootView.findViewById(R.id.edit_track_textView_nameTrack);
        editTextName.setText(TrackEditActivity.trackDetails.getName());

        seekBarDifficulty  = rootView.findViewById(R.id.edit_track_seekBar_difficulty);
        seekBarDifficulty.setProgress(TrackEditActivity.trackDetails.getDifficulty());

        textViewDistance = rootView.findViewById(R.id.text_view_edit_track_distance);
        TextView textViewTime = rootView.findViewById(R.id.text_view_edit_track_time);
        formatDistanceTextView();

        Double duration = Math.floor(TrackEditActivity.trackDetails.getDuration() / 1000);
        String durationString = "";
        if (duration > 60) {
            int durationMinute = (int)(Math.floor(duration / 60));
            int durationSeconde = (int)(Math.floor(duration % 60));

            if (durationMinute > 60) {
                int durationHeure = durationMinute / 60;
                durationString = durationHeure + "h " + durationMinute + "mn " + durationSeconde + "s";
            } else {
                durationString = durationMinute + "mn " + durationSeconde + "s";
            }
        }

        textViewTime.setText(durationString);


        final TextView textView = rootView.findViewById(R.id.tv_difficulty_detail_track);
        textView.setText(String.valueOf(TrackEditActivity.trackDetails.getDifficulty()));
        seekBarDifficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void formatDistanceTextView(){
        if (TrackEditActivity.trackDetails.getDistance() < 999) {
            textViewDistance.setText(Math.floor(TrackEditActivity.trackDetails.getDistance()*100)/100 + " m");
        } else {
            textViewDistance.setText(Math.floor(TrackEditActivity.trackDetails.getDistance())/1000 + " km");
        }
    }

    public void updateFiledsToDB(){
        TrackEditActivity.trackDetails.setName(editTextName.getText().toString());
        TrackEditActivity.trackDetails.setDifficulty(seekBarDifficulty.getProgress());
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
