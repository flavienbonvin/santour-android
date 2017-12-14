package ch.hesso.santour.view.Edition.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.SectionsPageAdapter;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Edition.Fragment.FragmentDetailsTrack;
import ch.hesso.santour.view.Edition.Fragment.FragmentListPOD;
import ch.hesso.santour.view.Edition.Fragment.FragmentListPOI;
import ch.hesso.santour.view.Edition.Fragment.FragmentListTracks;
import ch.hesso.santour.view.Tracking.Fragment.FragmentNewTrack;

public class TrackEditActivity extends AppCompatActivity {

    //Tabs layout adapter for fragment
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;


    public static Track trackDetails;
    public static FragmentListPOI fragmentListPOI;
    public static FragmentListPOD fragmentListPOD;


    private FragmentDetailsTrack fragmentDetailsTrack;

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
                fragmentDetailsTrack.updateFiledsToDB();
                TrackDB.update(trackDetails);
                this.finish();
                return true;
            case R.id.edition_action_bar_delete:
                showAlertDialog();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edition_activity);
        sectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        viewPager = findViewById(R.id.track_edit_container);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.edition_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Create the tabs used by the fragments
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(fragmentDetailsTrack = new FragmentDetailsTrack(), getString(R.string.edition_details_track));
        adapter.addFragment(fragmentListPOI = new FragmentListPOI(), getString(R.string.edition_details_list_poi));
        adapter.addFragment(fragmentListPOD = new FragmentListPOD(), getString(R.string.edition_details_list_pod));
        viewPager.setAdapter(adapter);
    }

    private void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm the deletion of the Track")
                .setMessage("Are you sur to delete this track?")
                //Close the dialog
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TrackDB.delete(trackDetails.getId(), new DBCallback() {
                            @Override
                            public void resolve(Object o) {
                                TrackEditActivity.this.finish();
                            }
                        });

                        TrackEditActivity.this.finish();
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