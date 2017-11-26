package ch.hesso.santour.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.SectionsPageAdapter;

public class TrackActivity extends AppCompatActivity {

    private static final String TAG = "TrackActivity";

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        sectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        viewPager = findViewById(R.id.track_container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.track_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(new TrackFragment(), getString(R.string.my_track));
        adapter.addFragment(new TrackPOIsListFragment(), getString(R.string.pois_list));
        adapter.addFragment(new TrackPODsListFragment(), getString(R.string.pods_list));
        viewPager.setAdapter(adapter);
    }
}
