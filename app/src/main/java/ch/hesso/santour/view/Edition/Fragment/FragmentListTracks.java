package ch.hesso.santour.view.Edition.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.POIListAdapter;
import ch.hesso.santour.adapter.TrackListAdapter;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.POI;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Tracking.Fragment.Recording.FragmentAddPOD;
import ch.hesso.santour.view.Tracking.Fragment.*;


public class FragmentListTracks extends Fragment {

    private View rootView;

    public FragmentListTracks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.edition_fragment_list_tracks, container, false);
        TrackDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<Track> listTrack = (ArrayList<Track>)o;
                final ListView list = rootView.findViewById(R.id.list_view_track);
                list.setAdapter(new TrackListAdapter(FragmentListTracks.this.getContext(), listTrack));

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Track t = (Track)adapterView.getItemAtPosition(i);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("track",t);

                        FragmentManager fragmentManager  = getFragmentManager();
                        Fragment fragment  = new FragmentDetailsTrack();
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.list_track_fragment, fragment).commit();
                    }
                });


                EditText editText = (EditText) rootView.findViewById(R.id.input_search_track);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ArrayList<Track> tracks = new ArrayList<>();
                        TrackListAdapter adapt = (TrackListAdapter)list.getAdapter();
                        ArrayList<Track> trackAdapter = adapt.getListData();

                        for (Track track : trackAdapter) {
                            if (track.getName().contains(s.toString())) {
                                tracks.add(track);
                            }
                        }
                        list.setAdapter(new TrackListAdapter(FragmentListTracks.this.getContext(), tracks));
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        });
        return rootView;
    }
}
