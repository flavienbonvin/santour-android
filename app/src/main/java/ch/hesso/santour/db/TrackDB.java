package ch.hesso.santour.db;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hesso.santour.model.Track;

/**
 * Created by Maxime on 18.11.2017.
 */

public class TrackDB {
    private static DatabaseReference tracksDB = FirebaseDatabase.getInstance().getReference("tracks");

    public static void add(Track track) {
        String id = tracksDB.push().getKey();
        tracksDB.child(id).setValue(track);
    }

    public static void add(Track track, final DBCallback callback) {
        final String id = tracksDB.push().getKey();
        tracksDB.child(id).setValue(track).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getById(id, callback);
            }
        });
    }

    public static void update(Track track) {
        tracksDB.child(track.id).setValue(track);
    }

    public static void update(final Track track, final DBCallback callback) {
        tracksDB.child(track.id).setValue(track).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getById(track.id, callback);
            }
        });
    }

    public static void getAll(final DBCallback callback) {
        Query q = tracksDB.orderByChild("name");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Track> users = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Track track = postSnapshot.getValue(Track.class);
                    track.id = postSnapshot.getKey();
                    users.add(track);
                }
                callback.resolve(users);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO implémentation du reject
            }
        };
        q.addValueEventListener(valueEventListener);
    }

    public static void getById(String id, final DBCallback callback) {
        Query q = tracksDB.child(id);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Track track = dataSnapshot.getValue(Track.class);
                track.id = dataSnapshot.getKey();
                callback.resolve(track);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO reject
            }
        };
        q.addValueEventListener(valueEventListener);
    }

    public static void delete(String id){
        tracksDB.child(id).removeValue();
    }

    public static void delete(String id, final DBCallback callback) {
        tracksDB.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.resolve(true);
            }
        });
    }
}
