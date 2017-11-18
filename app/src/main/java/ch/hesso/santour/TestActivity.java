package ch.hesso.santour;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.UserDB;
import ch.hesso.santour.model.User;

public class TestActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // lors du click sur le button on ouvre les documents
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                // in onCreate or any event where your want the user to
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });


        // on instancie le stockage firebase (les médias)
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // on instancie le stockage firebase (la DB)
        database = FirebaseDatabase.getInstance();
        // on autorise firebase a créer une copie locale des donées pour travailler sans connexion
        database.setPersistenceEnabled(true);

        // ici il suffit de coller des méthodes ecrit en dessous pour tester
        readUserById();
    }

    // pour le retour de la selection de fichier
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                Uri file = selectedImageUri;
                StorageReference riversRef = mStorageRef.child("images/test.jpg");

                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("Max",exception.getMessage());
                            }
                        });
            }
        }
    }



    private void addUser(){
        User u = new User("pseudoTemp", "emailTemp", "pass", "tracker");
        UserDB.addUser(u);
    }

    private void readAllUsers(){
        DBCallback callback = new DBCallback() {
            @Override
            public void resolve(Object o) {
                List<User> users = (List<User>)o;
                Log.d("maxTag",users.size()+" personnes");
            }
        };
        UserDB.getAllUsers(callback);
    }

    private void readUserById(){
        DBCallback callback = new DBCallback() {
            @Override
            public void resolve(Object o) {
                User u = (User)o;
                Log.d("maxTag", u.toString());

                //on lance l'update de mot de passe
                u.password = "changementDePassasd";
                UserDB.updateUser(u);
            }
        };
        UserDB.getUser("-KzDctVEm3NtjVd5vvxa",callback);

    }
}
