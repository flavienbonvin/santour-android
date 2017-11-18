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
    public String pseudo;
    public String email;
    public String password;
    public String typeUser;

    public User() {
    }

    public User(String pseudo, String email, String password, String typeUser) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.typeUser = typeUser;
    }
    public User(String id, String pseudo, String email, String password, String typeUser) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.typeUser = typeUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", typeUser='" + typeUser + '\'' +
                '}';
    }
}
