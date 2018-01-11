package ch.hesso.santour.view.Edition.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.POIListAdapter;
import ch.hesso.santour.view.Edition.Activity.TrackEditActivity;
import ch.hesso.santour.view.Edition.Activity.TrackEditPOIActivity;


public class FragmentListPOI extends Fragment {

    private View rootView;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    public FragmentListPOI() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_list_poi, container, false);

        final ListView list = rootView.findViewById(R.id.list_view_edit_poi);

        //Add a list of POI if there is any on the track
        if (TrackEditActivity.trackDetails.getPois().size() != 0) {
            POIListAdapter adapter;
            list.setAdapter(adapter = new POIListAdapter(FragmentListPOI.this.getContext(), TrackEditActivity.trackDetails.getPois()));

            //Open a new fragment once a POI is clicked
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //A modifier pour POI
                    Intent intent = new Intent(rootView.getContext(), TrackEditPOIActivity.class);
                    intent.putExtra("position",i);
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }

    public void updateList(){
        ListView list = rootView.findViewById(R.id.list_view_edit_poi);
        list.setAdapter(new POIListAdapter(FragmentListPOI.this.getContext(), TrackEditActivity.trackDetails.getPois()));
    }

}
