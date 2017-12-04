package ch.hesso.santour.view.Tracking.Fragment;

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
import ch.hesso.santour.view.Main.MainActivity;

public class FragmentListPOD extends Fragment {

    private View rootView;

    public FragmentListPOD() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tracking_fragment_recording_list_pod, container, false);

        ListView list = rootView.findViewById(R.id.list_view_pod);
        list.setAdapter(new PODListAdapter(FragmentListPOD.this.getContext(), MainActivity.track.getPods()));


        return rootView;
    }
}
