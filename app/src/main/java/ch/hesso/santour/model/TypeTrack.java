package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by flavien on 11/21/17.
 */

@IgnoreExtraProperties
public class TypeTrack {

    @Exclude
    private String id;
    private String name;

    public TypeTrack() { }

    public TypeTrack(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
