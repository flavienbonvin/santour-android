package ch.hesso.santour.view.Tracking.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;

import ch.hesso.santour.R;
import ch.hesso.santour.business.TrackingManagement;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Edition.Fragment.FragmentListTracks;
import ch.hesso.santour.view.Main.MainActivity;

public class FragmentEndTrack extends Fragment implements OnMapReadyCallback {

    //Google Map
    private MapView mapView;
    private GoogleMap map;

    public FragmentEndTrack() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tracking_fragment_end_track, container, false);
        setHasOptionsMenu(true);

        ((EditText)rootView.findViewById(R.id.edit_track_textView_nameTrack)).setText(MainActivity.track.getName());

        mapView = rootView.findViewById(R.id.edit_track_map_mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_close:
                MainActivity.track = new Track();
                getActivity().finish();
                return true;
            case R.id.action_bar_save:
                saveTrackAndRedirect();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.track_navigation_bar_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) throws SecurityException {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_json));
        map = googleMap;

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(false);

        PolylineOptions polylineOptions = new PolylineOptions().width(7).color(Color.parseColor("#52c7b8")).geodesic(true);

        LatLng coordinate = new LatLng(MainActivity.track.getPositions().get(0).latitude, MainActivity.track.getPositions().get(0).longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 18));

        for (Position position : MainActivity.track.getPositions()) {
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


    private void saveTrackAndRedirect(){
        SeekBar seekBarDifficulty = (SeekBar)this.getActivity().findViewById(R.id.edit_track_seekBar_difficulty);
        CheckBox checkBoxAccessibility = (CheckBox)this.getActivity().findViewById(R.id.edit_track_checkBox_accessForEveryone);
        EditText editTextPauses = (EditText)this.getActivity().findViewById(R.id.edit_track_editText_pauseDuration);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        MainActivity.track.setIdUser(auth.getCurrentUser().getUid());

        MainActivity.track.setDifficulty(seekBarDifficulty.getProgress());
        MainActivity.track.setForEveryone(checkBoxAccessibility.isChecked());

        if (!editTextPauses.getText().toString().equals(""))
            MainActivity.track.setPauseDuration(Integer.parseInt(editTextPauses.getText().toString()));

        TrackDB.add(MainActivity.track);

        getActivity().finish();
    }
}
