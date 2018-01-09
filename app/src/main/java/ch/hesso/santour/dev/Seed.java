package ch.hesso.santour.dev;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.hesso.santour.db.CategoryPODDB;
import ch.hesso.santour.db.CategoryPOIDB;
import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.UserDB;
import ch.hesso.santour.model.CategoryPOD;
import ch.hesso.santour.model.CategoryPOI;
import ch.hesso.santour.model.POD;
import ch.hesso.santour.model.POI;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.RatePOD;
import ch.hesso.santour.model.User;

/**
 * Created by Maxime on 18.11.2017.
 */

public class Seed {
    public Seed(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("users").removeValue();
        database.getReference("tracks").removeValue();
        database.getReference("category_pod").removeValue();
        database.getReference("category_poi").removeValue();

        User u = new User("max","max@max.com", "tracker");
        UserDB.add(u, new DBCallback() {
            @Override
            public void resolve(Object o) {
                final User u = (User) o;

                List<Position> pos = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    double lat = 40 + 0.1 * i;
                    double lont = 42 + 0.15 * i;
                    double alt = 800;
                    long time = Calendar.getInstance().getTimeInMillis();
                    pos.add(new Position(lat, lont, alt, time));
                }
                final List<Position> positionFinal = pos;


                CategoryPOD categoryPOD = new CategoryPOD("POD test 1");
                CategoryPODDB.add(categoryPOD, new DBCallback() {
                    @Override
                    public void resolve(Object o) {
                        CategoryPOD categoryPOD1 = (CategoryPOD) o;
                        List<RatePOD> categoryListPOD = new ArrayList<>();
                        RatePOD temp = new RatePOD(categoryPOD1.getId(),5);
                        categoryListPOD.add(temp);

                        List<POD> podList = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            POD pod = new POD("test POD", "description " + i, "temp", positionFinal.get(i), categoryListPOD);
                            podList.add(pod);
                        }
                        final List<POD> podFinal = podList;

                        CategoryPOI categoryPOI = new CategoryPOI("POI test 1");
                        CategoryPOIDB.add(categoryPOI, new DBCallback() {
                            @Override
                            public void resolve(Object o) {
                                CategoryPOI categoryPOI1 = (CategoryPOI) o;
                                List<String> categoryListPOI = new ArrayList<>();
                                categoryListPOI.add(categoryPOI1.getId());

                                List<POI> poiList = new ArrayList<>();
                                for (int i = 0; i < 5; i++){
                                    POI poi = new POI("test POI", "description " + i, "temp POI", positionFinal.get(i), categoryListPOI);
                                    poiList.add(poi);
                                }
                                final List<POI> poiFinal = poiList;

                                /*Track t = new Track("track1", 0, true, 2, u.id, positionFinal, podFinal, poiFinal, "test");
                                Log.d("FlavDEBUG", t.toString());

                                TrackDB.add(t, new DBCallback() {
                                    @Override
                                    public void resolve(Object o) {
                                        Track t = (Track) o;
                                        Log.d("Maxtag", t.toString());
                                    }
                                });*/

                            }
                        });
                    }
                });
            }
        });
    }
}
