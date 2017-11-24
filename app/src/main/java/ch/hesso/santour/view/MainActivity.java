package ch.hesso.santour.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.navigation_settings, menu);

        return super.onCreateOptionsMenu(menu);
    }

/*    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() > 0)
        {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_container);
            finish();
            super.onBackPressed();
        }
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        PermissionManagement.checkMandatoryPermission(MainActivity.this);

        //menu fragment
        fragmentManager = getFragmentManager();
        fragment = new MenuFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.main_container, fragment).commit();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
            case R.id.navigation_setting:

                fragment  = new SettingsFragment();
                fragmentManager  = getFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
        }
        return false;

    }

    public void test(){

    }

}



