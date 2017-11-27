package ch.hesso.santour.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ch.hesso.santour.R;

public class TrackPOIsListFragment extends Fragment {

    //Add POI / POD button
    private ImageButton addPOIButton;

    //Fragment
    private Fragment fragment;
    private FragmentManager fragmentManager;


    public TrackPOIsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_track_pois_list, container, false);
        addPOIButton = rootView.findViewById(R.id.track_pois_list_add_button);
        addPOIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager  = getFragmentManager();
                fragment  = new TrackAddPOIFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_content, fragment).commit();
            }
        });

        return rootView;
    }
}
