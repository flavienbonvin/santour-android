package ch.hesso.santour.business;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.view.Main.MainActivity;

/**
 * Created by maxim on 07.12.2017.
 */

public class PictureFirebaseManagement extends AsyncTask<Context, Void, Void> {

    private StorageReference storage;
    @Override
    protected Void doInBackground(final Context... contexts) {
        storage = FirebaseStorage.getInstance().getReference();
        String ret = "";
        try {
            InputStream inputStream = contexts[0].openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                String[] filenames = ret.split("\n");
                sendFileToFireBaseRecu(0, filenames, new DBCallback() {
                    @Override
                    public void resolve(Object o) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(contexts[0]);
                        builder.setTitle("Done");
                        builder.setMessage("Fini");
                        builder.show();
                    }
                });
            }
        }catch(Exception e){
            Log.e("maxError", e.getMessage());
        }
        return null;
    }

    private void sendFileToFireBaseRecu(final int i, final String[] paths, final DBCallback callback){
        StorageReference ref = storage.child("/"+paths[i]);

        String fileName = paths[i];

        Bitmap b = BitmapFactory.decodeFile(PictureManagement.localStoragePath+fileName);
        Bitmap bmRotated = PictureManagement.rotatePicture(b);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmRotated.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] byteArr = baos.toByteArray();

        UploadTask uploadTask = ref.putBytes(byteArr);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                int j = i+1;
                if(j==paths.length){
                    return;
                }
                sendFileToFireBaseRecu(j, paths, new DBCallback() {
                    @Override
                    public void resolve(Object o) {
                        callback.resolve(null);
                    }
                });
            }
        });
    }


    public static void downloadFile(String path){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String id = path.split("/")[0];
        File newFolder = new File(PictureManagement.localStoragePath+id);

        if(!newFolder.exists()) {

            newFolder.mkdirs();
        }

        File newFile = new File(PictureManagement.localStoragePath+ path);


        if(newFile.exists()){
            return;
        }
        try {
            newFile.createNewFile();
            URL u = new URL("https://firebasestorage.googleapis.com/v0/b/santour-c0a51.appspot.com/o/"+path.replace("/","%2F")+"?alt=media");

            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];

            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(newFile));
            fos.write(buffer);
            fos.flush();
            fos.close();

            Log.d("MaxDebug", "file wrotten to "+newFile.getAbsolutePath());

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }
}
