package ch.hesso.santour.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapter;
import ch.hesso.santour.adapter.CategoryListAdapterPOI;
import ch.hesso.santour.db.CategoryPOIDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.CategoryPOI;
import ch.hesso.santour.model.POI;

public class TrackPOIDetailsFragment extends Fragment {

    private ListView listView;
    private CategoryListAdapter adapter;
    private View rootView;

    private  POI poi;

    public TrackPOIDetailsFragment() {
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
                addPOIandBack();
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
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_track_poi_details, container, false);
        setHasOptionsMenu(true);
        setHasOptionsMenu(true);

        poi = (POI) this.getArguments().getSerializable("poi");

        CategoryPOIDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<CategoryPOI> categories = (ArrayList<CategoryPOI>) o;
                ListView listView = rootView.findViewById(R.id.track_poi_details_categories_list);
                listView.setAdapter(new CategoryListAdapterPOI(TrackPOIDetailsFragment.this.getContext(), categories));
            }
        });

        return  rootView;
    }

    private void addPOIandBack(){

        ListView list = rootView.findViewById(R.id.track_poi_details_categories_list);
        CategoryListAdapterPOI cat = (CategoryListAdapterPOI) list.getAdapter();

        poi.setCategoriesID(cat.getAll());
        MainActivity.track.addPOI(poi);

        FragmentManager manager = getActivity().getFragmentManager();

        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager
                    .getBackStackEntryAt(manager.getBackStackEntryCount()-2);
            manager.popBackStack(first.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}