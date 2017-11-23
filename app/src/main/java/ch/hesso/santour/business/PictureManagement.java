package ch.hesso.santour.business;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;



/**
 * Created by Maxime on 23.11.2017.
 */

public class PictureManagement extends Activity{
    public static final int REQUEST_IMAGE_CAPTURE = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePicture();
    }

    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 200);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == this.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            encodeBitmapBase64(imageBitmap);

        }
    }

    private void encodeBitmapBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        resolve(imageBitmap, imageEncoded);
    }

    private void resolve(Bitmap imageBitmap, String imageString){
        Intent intentMessage=new Intent();
        intentMessage.putExtra("imageBitmap",imageBitmap);
        intentMessage.putExtra("imageString",imageString);
        setResult(REQUEST_IMAGE_CAPTURE,intentMessage);
        finish();
    }

    public static Bitmap decodeBitmapBase64(String imageEncoded){
        byte[] decodedByteArray = android.util.Base64.decode(imageEncoded, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
