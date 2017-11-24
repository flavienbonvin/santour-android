package ch.hesso.santour;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
        Button btnCreateTrack = rootView.findViewById(R.id.createTrack);
        btnCreateTrack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                fragmentManager = getFragmentManager();
                fragment = new AddTrackFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });
        return  rootView;
    }
}
