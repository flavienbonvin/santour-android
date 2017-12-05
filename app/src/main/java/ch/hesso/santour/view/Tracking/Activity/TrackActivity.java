package ch.hesso.santour.view.Tracking.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.SectionsPageAdapter;
import ch.hesso.santour.view.Tracking.Fragment.FragmentListPOD;
import ch.hesso.santour.view.Tracking.Fragment.FragmentListPOI;
import ch.hesso.santour.view.Tracking.Fragment.FragmentRecording;

public class TrackActivity extends AppCompatActivity {

    //Activity tag
    private static final String TAG = "TrackActivity";

    //Tabs layout adapter for fragment
    public static FragmentListPOI fragmentListPOI;
    public static FragmentListPOD fragmentListPOD;

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_activity);
        sectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        viewPager = findViewById(R.id.track_container);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.track_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager)
    {

        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(new FragmentRecording(), getString(R.string.my_track));
        adapter.addFragment(fragmentListPOI = new FragmentListPOI(), getString(R.string.pois_list));
        adapter.addFragment(fragmentListPOD = new FragmentListPOD(), getString(R.string.pods_list));
        viewPager.setAdapter(adapter);
    }
}
