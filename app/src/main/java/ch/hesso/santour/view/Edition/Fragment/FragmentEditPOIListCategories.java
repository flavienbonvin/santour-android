package ch.hesso.santour.view.Edition.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapterPOI;
import ch.hesso.santour.db.CategoryPOIDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.CategoryPOI;
import ch.hesso.santour.view.Edition.Activity.TrackEditPOIActivity;


public class FragmentEditPOIListCategories extends Fragment {

    private View rootView;

    private CategoryListAdapterPOI adapter;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    public FragmentEditPOIListCategories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_poi_list_categories, container, false);

        final ListView list = rootView.findViewById(R.id.list_view_poi_categories_list);

        CategoryPOIDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<CategoryPOI> categories = (ArrayList<CategoryPOI>) o;
                ListView listView = rootView.findViewById(R.id.list_view_poi_categories_list);


                Log.e("test", TrackEditPOIActivity.poiDetails.toString());

                boolean[] isChecked = new boolean[categories.size()];

                if (TrackEditPOIActivity.poiDetails.getCategoriesID() != null) {
                    for (int i = 0; i < categories.size(); i++) {
                        for (int j = 0; j < TrackEditPOIActivity.poiDetails.getCategoriesID().size(); j++) {
                            if (categories.get(i).getId().equals(TrackEditPOIActivity.poiDetails.getCategoriesID().get(j))) {
                                isChecked[i] = true;
                            }
                        }
                    }
                }
                listView.setAdapter(new CategoryListAdapterPOI(rootView.getContext(), categories, isChecked));
            }
        });

        return rootView;

    }

}
