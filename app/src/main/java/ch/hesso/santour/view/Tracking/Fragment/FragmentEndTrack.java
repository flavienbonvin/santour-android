package ch.hesso.santour.view.Tracking.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;

import ch.hesso.santour.R;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Main.MainActivity;

public class FragmentEndTrack extends Fragment implements OnMapReadyCallback {

    //Google Map
    private MapView mapView;
    private GoogleMap map;

    private SeekBar seekBarDifficulty;

    public FragmentEndTrack() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tracking_fragment_end_track, container, false);
        setHasOptionsMenu(true);

        ((TextView)rootView.findViewById(R.id.edit_track_textView_nameTrack)).setText(MainActivity.track.getName());

        final TextView editTextDifficulty = rootView.findViewById(R.id.tv_difficulty_end_track);

        seekBarDifficulty = rootView.findViewById(R.id.edit_track_seekBar_difficulty);
        seekBarDifficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editTextDifficulty.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        mapView = rootView.findViewById(R.id.edit_track_map_mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_close:
                deleteTrackDialog();
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


        if (MainActivity.track.getPositions().size() > 0) {
            PolylineOptions polylineOptions = new PolylineOptions().width(7).color(Color.parseColor("#52c7b8")).geodesic(true);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Position position : MainActivity.track.getPositions()) {
                LatLng latLng = new LatLng(position.latitude, position.longitude);
                polylineOptions.add(latLng);
                builder.include(latLng);
            }

            LatLngBounds bounds = builder.build();

            map.clear();
            map.addPolyline(polylineOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

            LatLng startLatLng = new LatLng(MainActivity.track.getPositions().get(0).latitude, MainActivity.track.getPositions().get(0).longitude);
            map.addMarker(new MarkerOptions()
                    .position(startLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            int size = MainActivity.track.getPositions().size();
            LatLng endLatLng = new LatLng(MainActivity.track.getPositions().get(size - 1).latitude, MainActivity.track.getPositions().get(size - 1).longitude);
            map.addMarker(new MarkerOptions()
                    .position(endLatLng));
        }
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

    private void deleteTrackDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(FragmentEndTrack.this.getContext());
        builder.setTitle("Confirm the deletion of the track")
                .setMessage("Are you sur to delete this track?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.track = new Track();
                        FragmentEndTrack.this.getActivity().finish();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    private void saveTrackAndRedirect(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        MainActivity.track.setIdUser(auth.getCurrentUser().getUid());

        MainActivity.track.setDifficulty(seekBarDifficulty.getProgress());

        TrackDB.add(MainActivity.track, new DBCallback() {
            @Override
            public void resolve(Object o) {
                Toast.makeText(MainActivity.mainActivity.getBaseContext(), "Your track " + MainActivity.track.getName() + " has beed saved", Toast.LENGTH_SHORT).show();
            }
        });

        getActivity().finish();
    }
}
