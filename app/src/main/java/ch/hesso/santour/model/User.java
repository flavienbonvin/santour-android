package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Maxime on 18.11.2017.
 */

@IgnoreExtraProperties
public class User {
    @Exclude
    public String id;
    private String email;
    private String typeUser;
    private String idAuth;

    public User() {
    }

    public User(String email, String typeUser) {
        this.email = email;
        this.typeUser = typeUser;
    }
    public User(String id, String email, String typeUser) {
        this.id = id;
        this.email = email;
        this.typeUser = typeUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", typeUser='" + typeUser + '\'' +
                ", idAuth='" + idAuth + '\'' +
                '}';
    }
}
