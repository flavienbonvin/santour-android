package ch.hesso.santour.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import ch.hesso.santour.R;

public class TrackEditActivity extends AppCompatActivity {

    //Bottom Navigation Bar
    private BottomNavigationView navigation;

    private Fragment fragment;
    private FragmentManager fragmentManager;


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
                //Here save the datas modified

                //Toast.makeText(getContext(), this.getString(R.string.newcomicsaved), Toast.LENGTH_SHORT).show();
                //fragment = new ExchangeFragment();
                //fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

                return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_edit);
        //Bottom navigation
        navigation = findViewById(R.id.track_edit_bottom_navigation);
        navigation.inflateMenu(R.menu.track_edit_bottom_navigation);
        navigation.getMenu().getItem(0).setChecked(true);

        fragmentManager = getFragmentManager();
        fragment = new TrackEditListPOIFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.track_edit_container, fragment).commit();

        //Gestion BottomNavBar
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.track_bottom_navigation_track:
                        fragment = new TrackEditDetailsFragment();
                        setTitle(R.string.title_edit_details);
                        break;
                    case R.id.track_bottom_navigation_poi:
                        fragment = new TrackEditListPOIFragment();
                        setTitle(R.string.title_edit_list_poi);
                        break;
                    case R.id.track_bottom_navigation_pod:
                        fragment = new TrackEditListPODFragment();
                        setTitle(R.string.title_edit_list_pod);
                        break;
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.track_edit_container, fragment).commit();
                return true;
            }
        });


    }

}
