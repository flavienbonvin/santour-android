package ch.hesso.santour.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapter;
import ch.hesso.santour.db.CategoryPODDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.CategoryPOD;
import ch.hesso.santour.model.POD;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.RatePOD;

public class TrackPODDetailsFragment extends Fragment {

    private ListView listView;
    private CategoryListAdapter adapter;
    private View rootView;
    private POD pod;

    public TrackPODDetailsFragment() {
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
                addPODAndBack();
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
        rootView = inflater.inflate(R.layout.fragment_track_pod_details, container, false);
        setHasOptionsMenu(true);

        pod = (POD)getArguments().getSerializable("pod");


        CategoryPODDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<CategoryPOD> categories = (ArrayList<CategoryPOD>) o;
                ListView list = rootView.findViewById(R.id.track_pod_details_categories_list);
                list.setAdapter(new CategoryListAdapter(TrackPODDetailsFragment.this.getContext(),categories));
            }
        });

        return rootView;
    }

    private void addPODAndBack(){

        ListView list = rootView.findViewById(R.id.track_pod_details_categories_list);
        CategoryListAdapter cat = (CategoryListAdapter) list.getAdapter();

        pod.setCategoriesID(cat.getAllItems());

        Log.e("maxDebug", pod.toString());
        MainActivity.track.addPOD(pod);

        FragmentManager manager = getActivity().getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager
                    .getBackStackEntryAt(manager.getBackStackEntryCount()-2);
            manager.popBackStack(first.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
