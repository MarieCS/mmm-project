package com.example.mcs.mmm_project.pojo;

import java.io.Serializable;

public class Evaluation implements Serializable {
    public float rating;
    public String comment;

    public Evaluation() { }

    public Evaluation(float rating) {
        this.rating = rating;
    }

    public Evaluation(String comment) {
        this.comment = comment;
    }
}
