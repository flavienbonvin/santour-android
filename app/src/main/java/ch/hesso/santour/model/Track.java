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
    public List<POD> pods;
    public List<POI> pois;
    public String typeTrackID;

    public Track() {
        this.positions = new ArrayList<>();
        this.pods = new ArrayList<>();
        this.pois = new ArrayList<>();
    }

    public Track(String name){
        this.name = name;
    }

    public Track(String id, String name, int pauseDuration, boolean isForEveryone, int difficulty, String idUser, List<Position> positions, List<POD> pods, List<POI> pois, String typeTrackID) {
        this.id = id;
        this.name = name;
        this.pauseDuration = pauseDuration;
        this.isForEveryone = isForEveryone;
        this.difficulty = difficulty;
        this.idUser = idUser;
        this.positions = positions;
        this.pods = pods;
        this.pois = pois;
        this.typeTrackID = typeTrackID;
    }

    public Track(String name, int pauseDuration, boolean isForEveryone, int difficulty, String idUser, List<Position> positions, List<POD> pods, List<POI> pois, String typeTrackID) {
        this.name = name;
        this.pauseDuration = pauseDuration;
        this.isForEveryone = isForEveryone;
        this.difficulty = difficulty;
        this.idUser = idUser;
        this.positions = positions;
        this.pods = pods;
        this.pois = pois;
        this.typeTrackID = typeTrackID;
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
                ", dangers=" + pods +
                ", interests=" + pois +
                ", typeTrackID='" + typeTrackID + '\'' +
                '}';
    }
}
