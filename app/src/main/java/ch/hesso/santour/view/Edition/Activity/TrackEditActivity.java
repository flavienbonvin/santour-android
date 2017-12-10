package ch.hesso.santour.view.Edition.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

    private FragmentDetailsTrack fragmentDetailsTrack;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.navigation_check, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Handle check button activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.button_navigation_check:
                fragmentDetailsTrack.updateFiledsToDB();
                TrackDB.update(trackDetails);
                this.finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentDetailsTrack = new FragmentDetailsTrack();

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
        adapter.addFragment(fragmentDetailsTrack, getString(R.string.edition_details_track));
        adapter.addFragment(new FragmentListPOI(), getString(R.string.edition_details_list_poi));
        adapter.addFragment(new FragmentListPOD(), getString(R.string.edition_details_list_pod));
        viewPager.setAdapter(adapter);
    }
}
