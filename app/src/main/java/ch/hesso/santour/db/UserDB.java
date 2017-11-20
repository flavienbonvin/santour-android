package ch.hesso.santour.db;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import ch.hesso.santour.model.User;

/**
 * Created by Maxime on 18.11.2017.
 */

public class UserDB {
    private static DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference("users");

    public static void add(User u) {
        // on récupere l'id généré par firebase
        String id = usersDB.push().getKey();
        // on ajoute l'enfant dans la DB
        usersDB.child(id).setValue(u);
    }

    public static void add(User track, final DBCallback callback) {
        final String id = usersDB.push().getKey();
        usersDB.child(id).setValue(track).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getById(id, callback);
            }
        });
    }

    public static void update(User u) {
        usersDB.child(u.id).setValue(u);
    }

    public static void update(final User u, final DBCallback callback) {
        usersDB.child(u.id).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getById(u.id, callback);
            }
        });
    }

    public static void getAll(final DBCallback callback) {
        // on fait une query pour firebase
        Query q = usersDB.orderByChild("pseudo");
        // le callback quand firebase return une réponse
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // on transforme le snapshot dans la bonne classe
                    User user = postSnapshot.getValue(User.class);
                    // on lui ajoute son id
                    user.id = postSnapshot.getKey();
                    users.add(user);
                }
                // on appelle la methode resolve qui à été déclaré dans la class main
                callback.resolve(users);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO implémentation du reject
            }
        };
        // on ajoute l'event pour lancer la fonction à la fin du la requete async
        q.addValueEventListener(valueEventListener);
    }

    public static void getById(String id, final DBCallback callback) {
        // on récupère la bonne entrée
        Query q = usersDB.child(id);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.id = dataSnapshot.getKey();
                // on resolve une fois le user trouvé
                callback.resolve(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO reject
            }
        };

        q.addValueEventListener(valueEventListener);
    }

    public static void delete(String id) {
        usersDB.child(id).removeValue();
    }

    public static void delete(String id, final DBCallback callback) {
        usersDB.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.resolve(true);
            }
        });
    }
}
