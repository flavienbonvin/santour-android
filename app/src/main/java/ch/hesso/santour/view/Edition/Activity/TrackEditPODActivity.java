package ch.hesso.santour.view.Edition.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ch.hesso.santour.R;
import ch.hesso.santour.adapter.SectionsPageAdapter;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.view.Edition.Fragment.FragmentEditDetailsPOD;

public class TrackEditPODActivity extends AppCompatActivity {

    //Tabs layout adapter for fragment
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    public static Track podDetails;

    private FragmentEditDetailsPOD fragmentEditDetailsPOD;

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
                fragmentEditDetailsPOD.updateFiledsToDB();
                TrackDB.update(podDetails);
                this.finish();
                return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edition_activity_edit_pod);
    }
}
