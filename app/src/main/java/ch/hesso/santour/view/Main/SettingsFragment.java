package ch.hesso.santour.view.Main;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ch.hesso.santour.R;
import ch.hesso.santour.business.PreferenceDownload;

import static android.content.SharedPreferences.*;

public class SettingsFragment extends Fragment {

    private Fragment fragment;
    private FragmentManager fragmentManager;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_fragment_settings, container, false);

        Button buttonDownload = rootView.findViewById(R.id.bt_settings_server_download_file);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PreferenceDownload().execute();
            }
        });

        TextView minDistance = rootView.findViewById(R.id.settings_tv_minimal_dist_value);
        TextView seekStep = rootView.findViewById(R.id.settings_tv_seekbar_value);

        SharedPreferences sharedPref = SettingsFragment.this.getActivity().getPreferences(Context.MODE_PRIVATE);
        minDistance.setText(sharedPref.getString("minimanlDistance", "Missing"));
        seekStep.setText(sharedPref.getString("seekbarValue", "Missing"));

        return rootView;
    }
}
