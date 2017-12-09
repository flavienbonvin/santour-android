package ch.hesso.santour.view.Edition.Fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapter;


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

        //A changer pour avoir les catégories du poi selectionné
        /*
        if (TrackEditPODActivity.podDetails.getPoid().size() != 0) {
            list.setAdapter(adapter = new CategoryListAdapter(FragmentEditPODListCategories.this.getContext(), TrackEditPODActivity.podDetails.getPoid()));

        }
        */
        return rootView;
    }
}
