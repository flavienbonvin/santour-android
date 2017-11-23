package ch.hesso.santour.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavien on 11/21/17.
 */

public class POD {

    private String name;
    private String description;
    private String picture;
    private Position position;
    private List<RatePOD> categoriesID;


    public POD() {
        this.categoriesID = new ArrayList<>();
    }

    public POD(String name, String description, String picture, Position position, List<RatePOD> categoriesID) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.position = position;
        this.categoriesID = categoriesID;
    }

    public void addCategory(RatePOD categoryID){
        categoriesID.add(categoryID);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public Position getPosition() {
        return position;
    }

    public List<RatePOD> getCategoriesID() {
        return categoriesID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCategoriesID(List<RatePOD> categoriesID) {
        this.categoriesID = categoriesID;
    }

    @Override
    public String toString() {
        return "POI{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picturePath='" + picture + '\'' +
                ", position=" + position +
                ", categoriesID=" + categoriesID +
                '}';
    }
}
