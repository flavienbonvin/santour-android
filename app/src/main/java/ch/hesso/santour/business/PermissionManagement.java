package ch.hesso.santour.business;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Set;

/**
 * Created by flavien on 11/23/17.
 */

public class PermissionManagement extends ActivityCompat {

    public static final int PERMISSION_ALL = 1;

    //TODO Improve permission request, explain why we need them
    //TODO MIGHT NEED TO REMOVE THE WRITE_EXTERNAL_STORAGE
    public static String[] MANDATORY_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Initial methods used at to ensure that all the settings/permissions are set correctly
     * @param activity
     */
    public static void initialCheck(Activity activity) {
        checkMandatoryPermission(activity);
        checkHighAccuracyIsEnabled(activity);
    }

    /**
     * Display a popup asking the user to grant access to all mandatory permissions
     *
     * @param activity
     */
    protected static void checkMandatoryPermission(Activity activity) {
        if (!checkHasPermission(activity)) {
            ActivityCompat.requestPermissions(activity, MANDATORY_PERMISSIONS, PERMISSION_ALL);
        }
    }

    /**
     * Check if all the mandatory permissions are granted
     *
     * @param activity
     * @return boolean
     */
    private static boolean checkHasPermission(Activity activity) {
        for (String s : MANDATORY_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, s) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return false;
    }

    /**
     * Check that high accuracy location is enabled on the device (if location is disabled this will show the dialog too)
     * @param activity
     */
    public static void checkHighAccuracyIsEnabled(Activity activity){
        //Test that the setting is high accuracy
        try {
            if (Settings.Secure.getInt(activity.getContentResolver(), Settings.Secure.LOCATION_MODE) != Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                showDialog(activity, "Check that high accuracy is enabled!","You have to activate the location service!", Settings.ACTION_LOCATION_SOURCE_SETTINGS, true);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void checkAirplaneModeDisabled(Activity activity){
        try {
            if (Settings.Global.getInt(activity.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON,0) != 0){
                showDialog(activity, "You cannot have airplane mode activated!", "You have to disable airplane mode!", Settings.ACTION_AIRPLANE_MODE_SETTINGS, false);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Show a dialog prompting the user to change it's location settings to high accuracy
     * @param activity
     * @param message
     */
    private static void showDialog(final Activity activity, String message, String title, final String settings, final boolean finishActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(settings);
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (finishActivity) {
                            activity.finish();
                        }
                    }
                }).show();
    }
}
