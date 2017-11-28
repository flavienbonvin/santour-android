package ch.hesso.santour.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ch.hesso.santour.R;
import ch.hesso.santour.business.LocationManagement;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.Position;


public class TrackAddPODFragment extends Fragment {

    private Button nextButton;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    public TrackAddPODFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_bar_close:
                getActivity().getFragmentManager().popBackStack();
                return true;
            case R.id.action_bar_save:
                getActivity().getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.track_navigation_bar_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_track_add_pod, container, false);
        setHasOptionsMenu(true);

        nextButton = rootView.findViewById(R.id.track_add_pod_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager  = getFragmentManager();
                fragment  = new TrackPODDetailsFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_content, fragment).commit();
            }
        });

        LocationManagement.getCurrentPosition(getActivity(), new DBCallback() {
            @Override
            public void resolve(Object o) {
                Position position = (Position) o;

                TextView textViewLat = (TextView) getActivity().findViewById(R.id.tv_lat_add_pod);
                TextView textViewLng = (TextView) getActivity().findViewById(R.id.tv_lng_add_pod);

                textViewLat.setText("Lat: " + Math.floor(position.latitude*100)/100);
                textViewLng.setText("Lng: " + Math.floor(position.longitude*100)/100);
            }
        });

        return rootView;
    }
}
