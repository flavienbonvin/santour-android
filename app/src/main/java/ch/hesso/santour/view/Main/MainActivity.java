package ch.hesso.santour.view.Main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ch.hesso.santour.R;
import ch.hesso.santour.business.PermissionManagement;
import ch.hesso.santour.business.TrackingManagement;
import ch.hesso.santour.model.Track;

public class MainActivity extends AppCompatActivity {
    public static TrackingManagement trackingManagement = new TrackingManagement();
    public static Track track;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    private String[] drawerItemsList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.navigation_settings, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);

        //ici Raf
        drawerItemsList = getResources().getStringArray(R.array.items);
        drawerLayout = findViewById(R.id.main_drawer_layout);

        PermissionManagement.initialCheck(MainActivity.this);

        //menu fragment
        fragmentManager = getFragmentManager();
        fragment = new MenuFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
        setTitle("Menu");

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
        }
        return false;

    }
}



