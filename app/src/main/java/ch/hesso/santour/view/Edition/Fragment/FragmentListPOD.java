package ch.hesso.santour.view.Edition.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.PODListAdapter;
import ch.hesso.santour.adapter.POIListAdapter;
import ch.hesso.santour.view.Edition.Activity.TrackEditActivity;
import ch.hesso.santour.view.Main.MainActivity;
import ch.hesso.santour.view.Tracking.Fragment.Recording.FragmentAddPOI;

public class FragmentListPOD extends Fragment {

    private View rootView;
    private PODListAdapter adapter;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    public FragmentListPOD() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_list_pod, container, false);

        final ListView list = rootView.findViewById(R.id.list_view_pod);

        if(TrackEditActivity.trackDetails.getPods().size() != 0) {
            list.setAdapter(adapter = new PODListAdapter(FragmentListPOD.this.getContext(), TrackEditActivity.trackDetails.getPods()));

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fragmentManager  = getFragmentManager();
                    fragment = new FragmentEditDetailsPOD();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.edition_content, fragment).commit();
                }
            });
        }
        return rootView;
    }
}