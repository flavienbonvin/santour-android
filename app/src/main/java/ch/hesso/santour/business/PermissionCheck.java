package ch.hesso.santour.business;

import android.Manifest;
import android.app.Activity;
import android.support.v4.content.ContextCompat;

/**
 * Created by flavien on 11/23/17.
 */

public class PermissionCheck {


    //Check if the most importants permissions are still granted (position, camera, write external)
    public static void checkPermissionsStillGranted(Activity activity){
        int permissionPosition = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int permissionWriteExternal = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static void grantPermissions(Activity activity){
        
    }
}
