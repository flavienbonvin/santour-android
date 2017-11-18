package ch.hesso.santour.dev;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.hesso.santour.db.DBCallback;
import ch.hesso.santour.db.TrackDB;
import ch.hesso.santour.db.UserDB;
import ch.hesso.santour.model.Position;
import ch.hesso.santour.model.Track;
import ch.hesso.santour.model.User;

/**
 * Created by Maxime on 18.11.2017.
 */

public class Seed {
    public Seed(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("users").removeValue();
        database.getReference("tracks").removeValue();


        User u = new User("max","max@max.com","pass","tracker");
        UserDB.add(u, new DBCallback() {
            @Override
            public void resolve(Object o) {
                User u = (User) o;

                List<Position> pos = new ArrayList<>();
                for(int i = 0; i<5;i++){
                    double lat = 40+0.1*i;
                    double lont = 42+0.15*i;
                    double alt = 800;
                    long time  = Calendar.getInstance().getTimeInMillis();
                    pos.add(new Position(lat,lont,alt,time));
                }

                Track t = new Track("track1",0,true,2,u.id,pos);

                TrackDB.add(t, new DBCallback() {
                    @Override
                    public void resolve(Object o) {
                        Track t = (Track) o;
                        Log.d("Maxtag",t.toString());
                    }
                });

            }
        });
    }
}
