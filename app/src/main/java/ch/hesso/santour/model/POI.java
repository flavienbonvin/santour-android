package ch.hesso.santour.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavien on 11/21/17.
 */

public class POI {

    private String name;
    private String description;
    private String picturePath;
    private Position position;
    private List<String> categoriesID;


    public POI() {
        this.categoriesID = new ArrayList<>();
    }

    public POI(String name, String description, String picturePath, Position position, List<String> categoriesID) {
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.position = position;
        this.categoriesID = categoriesID;
    }

    public void addCategory(String categoryID){
        categoriesID.add(categoryID);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public Position getPosition() {
        return position;
    }

    public List<String> getCategoriesID() {
        return categoriesID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCategoriesID(List<String> categoriesID) {
        this.categoriesID = categoriesID;
    }

    @Override
    public String toString() {
        return "POI{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", position=" + position +
                ", categoriesID=" + categoriesID +
                '}';
    }
}
