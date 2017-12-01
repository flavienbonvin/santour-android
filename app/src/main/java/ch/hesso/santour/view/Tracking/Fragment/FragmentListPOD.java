package ch.hesso.santour.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.PODListAdapter;

public class TrackPODsListFragment extends Fragment {

    private View rootView;

    public TrackPODsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_track_pods_list, container, false);

        ListView list = rootView.findViewById(R.id.list_view_pod);
        list.setAdapter(new PODListAdapter(TrackPODsListFragment.this.getContext(),MainActivity.track.getPods()));


        return rootView;
    }
}
