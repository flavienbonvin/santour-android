package ch.hesso.santour.view.Edition.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.TrackListAdapter;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Track;


public class TrackListFragment extends Fragment {

    private View rootView;

    public TrackListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_track_list, container, false);
        TrackDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<Track> listTrack = (ArrayList<Track>)o;
                ListView list = rootView.findViewById(R.id.list_view_track);
                list.setAdapter(new TrackListAdapter(TrackListFragment.this.getContext(), listTrack));
            }
        });
        return rootView;
    }
}
