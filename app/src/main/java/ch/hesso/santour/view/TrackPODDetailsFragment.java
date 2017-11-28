package ch.hesso.santour.view;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapter;
import ch.hesso.santour.db.CategoryPODDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.CategoryPOD;

public class TrackPODDetailsFragment extends Fragment {

    private View rootView;

    public TrackPODDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView = inflater.inflate(R.layout.fragment_track_pod_details, container, false);
        CategoryPODDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<CategoryPOD> listPodCategory = (ArrayList<CategoryPOD>)o;
                ListView list = rootView.findViewById(R.id.track_pod_details_categories_list);
                list.setAdapter(new CategoryListAdapter(TrackPODDetailsFragment.this.getContext(), listPodCategory));
            }
        });

        return rootView;
    }
}
