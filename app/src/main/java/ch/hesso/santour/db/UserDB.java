package ch.hesso.santour.db;

import android.util.Log;

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

    public static void addUser(User u) {
        // on récupere l'id généré par firebase
        String id = usersDB.push().getKey();
        // on ajoute l'enfant dans la DB
        usersDB.child(id).setValue(u);
    }

    public static void updateUser(User u) {
        // on récupère le user sur le bon id et on l'update
        usersDB.child(u.id).setValue(u);
    }

    public static void getAllUsers(final DBCallback callback) {
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

    public static void getUser(String id, final DBCallback callback) {
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
}
