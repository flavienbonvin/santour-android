package ch.hesso.santour.view.Main;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ch.hesso.santour.R;
import ch.hesso.santour.business.PreferenceDownload;
import ch.hesso.santour.db.DBCallback;

public class SettingsFragment extends Fragment {

    private Fragment fragment;
    private FragmentManager fragmentManager;


    private String[] lang = {"fr", "en", "de"};

    private Spinner spinner;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.track_navigation_bar_actions_ok, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_fragment_settings, container, false);
        getActivity().setTitle(R.string.settings);
        setHasOptionsMenu(true);



        final SharedPreferences sharedPref = SettingsFragment.this.getActivity().getPreferences(Context.MODE_PRIVATE);

        final TextView minDistance = rootView.findViewById(R.id.settings_tv_minimal_dist_value);
        final TextView seekStep = rootView.findViewById(R.id.settings_tv_seekbar_value);


        Button buttonDownload = rootView.findViewById(R.id.bt_settings_server_download_file);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PreferenceDownload(true).execute();
                minDistance.setText(sharedPref.getString("minimalDistance", "Missing"));
                seekStep.setText(sharedPref.getString("seekbarValue", "Missing"));
                Log.d(SettingsFragment.class.getCanonicalName(), "min: " +  sharedPref.getString("minimalDistance", "Missing") + " seek: " + sharedPref.getString("seekbarValue", "Missing"));

            }
        });

        minDistance.setText(sharedPref.getString("minimalDistance", "Missing"));
        seekStep.setText(sharedPref.getString("seekbarValue", "Missing"));


        spinner = (Spinner)rootView.findViewById(R.id.settings_spinner_language);



        String lang = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("lang", "fr");
        switch (lang){
            case "fr":
                spinner.setSelection(0);
                break;
            case "en":
                spinner.setSelection(1);
                break;
            case "de":
                spinner.setSelection(2);
                break;
        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_save:
                changeToFR(getView(), lang[spinner.getSelectedItemPosition()]);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeToFR(View v, String lang) {
        String languageToLoad = lang;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, v.getResources().getDisplayMetrics());

        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("lang", languageToLoad).commit();
        getActivity().finish();
        Intent myIntent = new Intent(v.getContext(), MainActivity.class);
        startActivity(myIntent);
    }
}
