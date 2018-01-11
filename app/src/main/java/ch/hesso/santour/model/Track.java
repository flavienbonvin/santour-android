package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 18.11.2017.
 */

@IgnoreExtraProperties
public class Track implements Serializable{
    @Exclude
    private String id;
    private String name;
    private long duration;
    private int difficulty;
    private double distance;
    private String idUser;
    private List<Position> positions;
    private List<POD> pods;
    private List<POI> pois;

    public Track() {
        this.positions = new ArrayList<>();
        this.pods = new ArrayList<>();
        this.pois = new ArrayList<>();
        this.distance = 0;
    }

    public Track(String id, String name, int pauseDuration, long duration, int difficulty, String idUser, List<Position> positions, List<POD> pods, List<POI> pois, String typeTrackID) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.difficulty = difficulty;
        this.idUser = idUser;
        this.positions = positions;
        this.pods = pods;
        this.pois = pois;
    }

    public Track(String name, int pauseDuration, long duration, int difficulty, String idUser, List<Position> positions, List<POD> pods, List<POI> pois, String typeTrackID) {
        this.name = name;
        this.duration = duration;
        this.difficulty = difficulty;
        this.idUser = idUser;
        this.positions = positions;
        this.pods = pods;
        this.pois = pois;
    }

    public void addPosition(Position p){
        this.positions.add(p);
    }
    public void addPOD(POD pod){this.pods.add(pod);}

    public void addPOI(POI poi){this.pois.add(poi);}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<POD> getPods() {
        return pods;
    }

    public void setPods(List<POD> pods) {
        this.pods = pods;
    }

    public List<POI> getPois() {
        return pois;
    }

    public void setPois(List<POI> pois) {
        this.pois = pois;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", idUser='" + idUser + '\'' +
                ", positions=" + positions +
                ", dangers=" + pods +
                ", interests=" + pois +
                '}';
    }
}
