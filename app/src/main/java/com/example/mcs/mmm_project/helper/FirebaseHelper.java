package com.example.mcs.mmm_project.helper;

import com.example.mcs.mmm_project.pojo.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    public static void update(Event event) {
        DatabaseReference fbEvent = FirebaseDatabase.getInstance().getReference("features").child(event.getFirebaseIndex()).child("properties");

        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("taux_remplissage", event.taux_remplissage);

        String uuid = StringHelper.getUniqueID();
        updateFields.put("evaluations/" + uuid, event.evaluations.get(uuid));

        fbEvent.updateChildren(updateFields);
    }
}
