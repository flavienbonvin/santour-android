package ch.hesso.santour.business;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.hesso.santour.TestActivity;
import ch.hesso.santour.view.Main.MainActivity;


/**
 * Created by Maxime on 23.11.2017.
 */

public class PictureManagement extends Activity{
    public static final int REQUEST_IMAGE_CAPTURE = 111;
    private String mCurrentPhotoPath;
    private StorageReference storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePicture();
        storage = FirebaseStorage.getInstance().getReference();
    }

    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(PictureManagement.this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 200);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == this.RESULT_OK) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            StorageReference ref = storage.child(MainActivity.track.getId()+"/"+timeStamp+".jpg");


            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Matrix m = new Matrix();
            m.setRotate(90);
            Bitmap bmRotated = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), m , true);

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
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    resolve(mCurrentPhotoPath);
                }
            });

            //resizeImage(bmRotated);

        }
    }

    private void resizeImage(Bitmap imageBitmap){
        int newHeight = 800;
        double facteur = ((double)(newHeight)/imageBitmap.getHeight());
        int newWidth = (int)Math.round(imageBitmap.getWidth() * facteur);
        Log.d("maxDebug", "facteur "+facteur+" newHeight "+newHeight+" newWidth "+newWidth);
        Bitmap small = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, false);
        encodeBitmapBase64(small);
    }
    private void encodeBitmapBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        resolve(imageEncoded);
    }

    private void resolve(String imageString){
        Intent intentMessage=new Intent();
        intentMessage.putExtra("imageString",imageString);
        setResult(REQUEST_IMAGE_CAPTURE,intentMessage);
        finish();
    }

    public static Bitmap decodeBitmapBase64(String imageEncoded){
        byte[] decodedByteArray = android.util.Base64.decode(imageEncoded, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
