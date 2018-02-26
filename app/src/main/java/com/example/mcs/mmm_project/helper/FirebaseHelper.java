package com.example.mcs.mmm_project.helper;

import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.Parcours;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    public static void update(Event event) {
        DatabaseReference fbEvent = FirebaseDatabase.getInstance().getReference("features").child(event.firebaseIndex).child("properties");

        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("taux_remplissage", event.taux_remplissage);
        updateFields.put("evaluations/" + StringHelper.getUniqueID(), event.getUserEvaluation());

        fbEvent.updateChildren(updateFields);
    }

    public static void getEvaluations(Event event, ChildEventListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("features").child(event.firebaseIndex).child("properties").child("evaluations");
        mDatabase.addChildEventListener(listener);
    }

    public static void updateRoute() {
        ArrayList<Event> parcours = Parcours.getInstance().getParcours();

        FirebaseDatabase.getInstance().getReference("routes").child(StringHelper.getUniqueID()).removeValue();

        for(Event e : parcours) {
            Field[] fields = Event.class.getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("serialVersionUID") && !field.getName().equals("$change")) {
                    try { // Une connexion par valeur car sinon StackOverflow :)
                        FirebaseDatabase.getInstance()
                                .getReference("routes")
                                .child(StringHelper.getUniqueID())
                                .child(e.firebaseIndex)
                                .child("properties/")
                                .child(field.getName())
                                .setValue(field.get(e));
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public static void getRoute(ChildEventListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("routes").child(StringHelper.getUniqueID());
        mDatabase.addChildEventListener(listener);
    }
}
