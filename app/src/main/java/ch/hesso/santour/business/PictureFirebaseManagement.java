package ch.hesso.santour.business;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.hesso.santour.db.DBCallback;

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

}
