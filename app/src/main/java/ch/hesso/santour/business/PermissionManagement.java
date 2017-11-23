package ch.hesso.santour.business;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by flavien on 11/23/17.
 */

public class PermissionManagement extends ActivityCompat {

    public static final int PERMISSION_ALL = 1;

    //TODO Improve permission request, explain why we need them


    //TODO MIGHT NEED TO REMOVE THE WRITE_EXTERNAL_STORAGE
    public static String [] MANDATORY_PERMISSIONS = {
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Display a popup asking the user to grant access to all mandatory permissions
     * @param activity
     */
    public static void checkMandatoryPermission(Activity activity){
        if(!checkHasPermission(activity)){
            ActivityCompat.requestPermissions(activity, MANDATORY_PERMISSIONS, PERMISSION_ALL);
        }
    }

    /**
     * Check if all the mandatory permissions are granted
     * @param activity
     * @return boolean
     */
    private static boolean checkHasPermission(Activity activity){
        for (String s: MANDATORY_PERMISSIONS) {
            if(ContextCompat.checkSelfPermission(activity, s) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return false;
    }


}
