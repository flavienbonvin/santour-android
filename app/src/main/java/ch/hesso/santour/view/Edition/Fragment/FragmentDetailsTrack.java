package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hesso.santour.R;

/**
 * Created by flavien on 12/4/17.
 */

public class FragmentDetailsTrack extends Fragment {

    public FragmentDetailsTrack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edition_fragment_details_track, container, false);
    }
}
