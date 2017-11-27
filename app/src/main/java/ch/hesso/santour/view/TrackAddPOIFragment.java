package ch.hesso.santour.view;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import ch.hesso.santour.R;

public class TrackAddPOIFragment extends Fragment {

    public TrackAddPOIFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_track_add_poi, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }
}
