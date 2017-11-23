package ch.hesso.santour.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.hesso.santour.R;

public class TrackActivity extends AppCompatActivity {


    //Bottom Navigation Bar
    private BottomNavigationView navigation;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        //Bottom navigation
        navigation = findViewById(R.id.track_bottom_navigation);
        navigation.inflateMenu(R.menu.track_bottom_navigation);
        navigation.getMenu().getItem(0).setChecked(true);

        fragmentManager = getFragmentManager();
        fragment = new TrackFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.main_container, fragment).commit();

    }
}
