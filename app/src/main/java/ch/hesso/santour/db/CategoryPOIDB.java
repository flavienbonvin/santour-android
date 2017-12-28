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

import ch.hesso.santour.model.CategoryPOI;

/**
 * Created by Maxime on 21.11.2017.
 */

public class CategoryPOIDB {
    private static boolean bool = false;
    private static DatabaseReference categoryDB = FirebaseDatabase.getInstance().getReference("category_poi");

    public static void add(CategoryPOI category) {
        checkPersistance();
        String id = categoryDB.push().getKey();
        categoryDB.child(id).setValue(category);
    }

    public static void add(CategoryPOI category, final DBCallback callback) {
        checkPersistance();
        final String id = categoryDB.push().getKey();
        categoryDB.child(id).setValue(category).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getById(id, callback);
            }
        });
    }

    public static void update(CategoryPOI category) {
        checkPersistance();
        categoryDB.child(category.getId()).setValue(category);
    }

    public static void update(final CategoryPOI category, final DBCallback callback) {
        checkPersistance();
        categoryDB.child(category.getId()).setValue(category).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getById(category.getId(), callback);
            }
        });
    }

    public static void getAll(final DBCallback callback) {
        checkPersistance();
        Query q = categoryDB.orderByChild("name");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CategoryPOI> categories = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CategoryPOI category = postSnapshot.getValue(CategoryPOI.class);
                    category.setId(postSnapshot.getKey());
                    categories.add(category);
                }
                callback.resolve(categories);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO implémentation du reject
            }
        };
        q.addValueEventListener(valueEventListener);
    }

    public static void getById(String id, final DBCallback callback) {
        checkPersistance();
        Query q = categoryDB.child(id);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CategoryPOI category = dataSnapshot.getValue(CategoryPOI.class);
                category.setId(dataSnapshot.getKey());
                callback.resolve(category);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO reject
            }
        };
        q.addValueEventListener(valueEventListener);
    }

    public static void delete(String id){
        checkPersistance();
        categoryDB.child(id).removeValue();
    }

    public static void delete(String id, final DBCallback callback) {
        checkPersistance();
        categoryDB.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.resolve(true);
            }
        });
    }

    private static void checkPersistance(){
        if(!bool){
            categoryDB.keepSynced(true);
            bool = true;
        }
    }
}
