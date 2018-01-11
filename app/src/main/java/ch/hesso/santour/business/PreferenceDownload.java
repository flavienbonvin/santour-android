package ch.hesso.santour.business;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import ch.hesso.santour.R;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.view.Main.MainActivity;
import ch.hesso.santour.view.Main.MainFullScreenPictureActivity;
import ch.hesso.santour.view.Tracking.Activity.TrackActivity;
import ch.hesso.santour.view.Tracking.Fragment.FragmentListPOD;

/**
 * Created by flavien on 12/28/17.
 */

public class PreferenceDownload extends AsyncTask<String, String, String> {

    private boolean needToast;
    public PreferenceDownload(boolean needToast){
        this.needToast = needToast;
    }

    @Override
    protected String doInBackground(String... strings) {
        int count;
        try {
            URL url = new URL(MainActivity.URL_SETTINGS);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            String finalString = "";
            while ((str = in.readLine()) != null) {
                finalString = str;
            }
            in.close();
            Log.e("MaxDebug",finalString);
           preferenceUpdate(finalString);
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    private void preferenceUpdate(String settings){

        String[] settingArray = settings.split(",");

        SharedPreferences sharedPref = MainActivity.mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingArray[0].split(":")[0], settingArray[0].split(":")[1]);
        editor.putString(settingArray[1].split(":")[0], settingArray[1].split(":")[1]);
        editor.commit();

    }

    @Override
    protected void onPostExecute(String s) {
        if(needToast) {
            Toast.makeText(MainActivity.mainActivity.getBaseContext(), R.string.settings_updated, Toast.LENGTH_LONG).show();
        }
    }
}