package ch.hesso.santour.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ch.hesso.santour.R;
import ch.hesso.santour.view.AddTrackFragment;


public class MenuFragment extends Fragment {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_menu, container, false);


        //button create track
        Button btnCreateTrack = rootView.findViewById(R.id.menu_button_createTrack);
        btnCreateTrack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new AddTrackFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                getActivity().setTitle("Create a track");
            }
        });

        //button list track
        Button btnListTrack = rootView.findViewById(R.id.menu_button_listTrack);
        btnListTrack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new TrackListFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                getActivity().setTitle("List of tracks");
            }
        });
        return  rootView;
    }
}
