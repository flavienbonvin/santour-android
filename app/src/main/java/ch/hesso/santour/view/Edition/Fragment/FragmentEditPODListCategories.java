package ch.hesso.santour.view.Edition.Fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapter;
import ch.hesso.santour.db.CategoryPODDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.model.CategoryPOD;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Edition.Activity.TrackEditActivity;
import ch.hesso.santour.view.Edition.Activity.TrackEditPODActivity;
import ch.hesso.santour.view.Tracking.Fragment.Recording.FragmentCategoriesPOD;


public class FragmentEditPODListCategories extends Fragment {

    private View rootView;

    private CategoryListAdapter adapter;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    public FragmentEditPODListCategories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.edition_fragment_pod_list_categories, container, false);

        final ListView list = rootView.findViewById(R.id.list_view_pod_categories_list);

        CategoryPODDB.getAll(new DBCallback() {
            @Override
            public void resolve(Object o) {
                ArrayList<CategoryPOD> categories = (ArrayList<CategoryPOD>) o;
                ListView list = rootView.findViewById(R.id.list_view_pod_categories_list);

                int[] ratingTab = new int[categories.size()];

                if (TrackEditPODActivity.podDetails.getCategoriesID() != null) {
                    for (int i = 0; i < categories.size(); i++) {
                        for (int j = 0; j < TrackEditPODActivity.podDetails.getCategoriesID().size(); j++) {
                            if (categories.get(i).getId().equals(TrackEditPODActivity.podDetails.getCategoriesID().get(j).getPodCatID())) {
                                ratingTab[i] = TrackEditPODActivity.podDetails.getCategoriesID().get(j).getRate();
                            }
                        }
                    }
                }

                list.setAdapter(new CategoryListAdapter(rootView.getContext(), categories, ratingTab));

            }
        });
        return rootView;
    }
}
