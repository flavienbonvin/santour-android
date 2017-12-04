package ch.hesso.santour.view.Tracking.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hesso.santour.R;

public class FragmentListPOI extends Fragment {

    public FragmentListPOI() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.tracking_fragment_list_poi, container, false);
        return rootView;
    }
}
