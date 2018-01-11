package ch.hesso.santour.view.Edition.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.CategoryListAdapterPOI;
import ch.hesso.santour.adapter.SectionsPageAdapter;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.POI;

import ch.hesso.santour.view.Edition.Fragment.FragmentEditDetailsPOI;
import ch.hesso.santour.view.Edition.Fragment.FragmentEditPOIListCategories;

public class TrackEditPOIActivity extends AppCompatActivity {

    public static POI poiDetails;
    private int positionPOI;

    private FragmentEditDetailsPOI fragmentEditDetailsPOI;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.edition_track_navigation_bar_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Handle check button activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edition_action_bar_save:
                savePOI();
                this.finish();
                return true;
            case R.id.edition_action_bar_delete:
                showAlertDialog();
                return true;
        }
        return false;
    }

    private void savePOI() {
        EditText editNamePOI  = findViewById(R.id.edit_track_textView_namePOI);
        EditText editDescrPOI = findViewById(R.id.edit_poi_textView_descriptionContent);
        TrackEditActivity.trackDetails.getPois().get(positionPOI).setName(editNamePOI.getText().toString());
        TrackEditActivity.trackDetails.getPois().get(positionPOI).setDescription(editDescrPOI.getText().toString());

        ListView list = findViewById(R.id.list_view_poi_categories_list);
        CategoryListAdapterPOI adapter = (CategoryListAdapterPOI) list.getAdapter();
        TrackEditActivity.trackDetails.getPois().get(positionPOI).setCategoriesID(adapter.getAll());

        TrackEditActivity.fragmentListPOI.updateList();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positionPOI = getIntent().getIntExtra("position", -1);
        poiDetails = TrackEditActivity.trackDetails.getPois().get(positionPOI);

        setContentView(R.layout.edition_activity_edit_poi);
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        fragmentEditDetailsPOI = new FragmentEditDetailsPOI();


        ViewPager viewPager = findViewById(R.id.track_edit_container_poi);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.edition_tabs_POI);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(fragmentEditDetailsPOI, getString(R.string.edition_details_poi));
        adapter.addFragment(new FragmentEditPOIListCategories(), getString(R.string.edition_categories_poi));
        viewPager.setAdapter(adapter);
    }

    private void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm the deletion of the POI")
                .setMessage("Are you sur to delete this POI?")
                //Close the dialog
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TrackEditActivity.trackDetails.getPois().remove(positionPOI);
                        TrackDB.update(TrackEditActivity.trackDetails, new DBCallback() {
                            @Override
                            public void resolve(Object o) {
                                TrackEditPOIActivity.this.finish();
                            }
                        });
                        TrackEditActivity.fragmentListPOI.updateList();
                        dialog.cancel();
                    }
                })
                //Delete the POI
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
