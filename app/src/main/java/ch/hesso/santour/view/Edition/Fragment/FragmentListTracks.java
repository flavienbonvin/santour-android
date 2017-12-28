package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.TrackListAdapter;
import ch.hesso.santour.business.PermissionManagement;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Edition.Activity.TrackEditActivity;


public class FragmentListTracks extends Fragment {

    private View rootView;

    public FragmentListTracks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.edition_fragment_list_tracks, container, false);

        Log.d(FragmentListTracks.class.getCanonicalName(), "Before download");
        TrackDB.getAllByIdUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),new DBCallback() {
            @Override
            public void resolve(Object o) {

                Log.d(FragmentListTracks.class.getCanonicalName(), "After download");

                ArrayList<Track> listTrack = (ArrayList<Track>) o;
                final ListView list = rootView.findViewById(R.id.list_view_track);
                list.setAdapter(new TrackListAdapter(FragmentListTracks.this.getContext(), listTrack));

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Set the static trackDetails to the one clicked on the list
                        TrackEditActivity.trackDetails = (Track) adapterView.getItemAtPosition(i);

                        Intent intent = new Intent(rootView.getContext(), TrackEditActivity.class);
                        startActivity(intent);
                    }
                });

                //Search in the listView
                EditText editText = (EditText) rootView.findViewById(R.id.input_search_track);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d(FragmentListTracks.class.getCanonicalName(), "Click on item");

                        ArrayList<Track> tracks = new ArrayList<>();
                        TrackListAdapter adapt = (TrackListAdapter) list.getAdapter();
                        ArrayList<Track> trackAdapter = adapt.getListData();

                        for (Track track : trackAdapter) {
                            if (track.getName().contains(s.toString())) {
                                tracks.add(track);
                            }
                        }
                        //Change the adatper with the list of results of the search
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

    @Override
    public void onResume() {
        super.onResume();
        PermissionManagement.checkAirplaneModeDisabled(FragmentListTracks.this.getActivity());
    }
}
