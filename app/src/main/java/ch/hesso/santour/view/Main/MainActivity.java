package ch.hesso.santour.view.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ch.hesso.santour.R;
import ch.hesso.santour.business.PermissionManagement;
import ch.hesso.santour.business.TrackingManagement;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Edition.Fragment.FragmentListTracks;
import ch.hesso.santour.view.Tracking.Activity.TrackActivity;
import ch.hesso.santour.view.Tracking.Fragment.FragmentNewTrack;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static TrackingManagement trackingManagement = new TrackingManagement();
    public static Track track;
    public static MainActivity mainActivity;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    private String[] drawerItemsList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);

        //Navigation Drawer
        drawerItemsList = getResources().getStringArray(R.array.items);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation);


        PermissionManagement.initialCheck(MainActivity.this);

        //menu fragment
        fragmentManager = getFragmentManager();
        fragment = new FragmentListTracks();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
        setTitle("List of trackss");

        //Navigation Listener
        navigationView.setNavigationItemSelectedListener(this);

        mainActivity = MainActivity.this;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {

            case R.id.navigation_setting:
                fragmentManager = this.getFragmentManager();
                fragment = new SettingsFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                setTitle("Settings");
                return true;
            case android.R.id.home:
                handleNavigation();
                return true;
        }
        return false;

    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        switch (item.getItemId()) {
            case R.id.main_navigation_item1:
                fragment = new FragmentNewTrack();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                setTitle("Create a track");
                handleNavigation();
                track = new Track();
                return true;
            case R.id.main_navigation_item2:
                fragment = new FragmentListTracks();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                setTitle("List of tracks");
                handleNavigation();
                return true;
            case R.id.main_navigation_item3:
                fragment = new SettingsFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                setTitle("Settings");
                handleNavigation();
                return true;
        }
        return false;

    }

    public void handleNavigation()
    {
        if(drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else
            drawerLayout.openDrawer(Gravity.START);
    }
}



