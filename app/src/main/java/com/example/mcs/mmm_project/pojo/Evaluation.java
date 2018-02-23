package com.example.mcs.mmm_project.pojo;

import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class Evaluation implements Serializable {
    @Exclude
    public String uuidKey;
    public float rating;
    public String comment;

    public Evaluation() { }

    @Exclude
    public boolean isEmpty() {
        return rating == 0 && (comment == null || comment.trim().length() == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Evaluation that = (Evaluation) o;

        if (uuidKey != null && uuidKey.equals(that.uuidKey)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return rating + ": " + comment + " " + uuidKey;
    }
}
