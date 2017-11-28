package ch.hesso.santour.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapter;

public class TrackPOIDetailsFragment extends Fragment {

    private ListView listView;
    private CategoryListAdapter adapter;

    public TrackPOIDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_poi_details, container, false);
    }
}
