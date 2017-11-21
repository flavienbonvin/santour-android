package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 18.11.2017.
 */

@IgnoreExtraProperties
public class Track {
    @Exclude
    public String id;
    public String name;
    public int pauseDuration;
    public boolean isForEveryone;
    public int difficulty;
    public String idUser;
    public List<Position> positions;
    public List<POD> dangers;
    public List<POI> interests;

    public Track() {
        this.positions = new ArrayList<>();
        this.dangers = new ArrayList<>();
        this.interests = new ArrayList<>();
    }

    public Track(String id, String name, int pauseDuration, boolean isForEveryone, int difficulty, String idUser, List<Position> positions, List<POD> dangers, List<POI> interests) {
        this.id = id;
        this.name = name;
        this.pauseDuration = pauseDuration;
        this.isForEveryone = isForEveryone;
        this.difficulty = difficulty;
        this.idUser = idUser;
        this.positions = positions;
        this.dangers = dangers;
        this.interests = interests;
    }

    public Track(String name, int pauseDuration, boolean isForEveryone, int difficulty, String idUser, List<Position> positions, List<POD> dangers, List<POI> interests) {
        this.name = name;
        this.pauseDuration = pauseDuration;
        this.isForEveryone = isForEveryone;
        this.difficulty = difficulty;
        this.idUser = idUser;
        this.positions = positions;
        this.dangers = dangers;
        this.interests = interests;
    }

    public void addPosition(Position p){
        this.positions.add(p);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pauseDuration=" + pauseDuration +
                ", isForEveryone=" + isForEveryone +
                ", difficulty=" + difficulty +
                ", idUser='" + idUser + '\'' +
                ", positions=" + positions +
                '}';
    }
}
