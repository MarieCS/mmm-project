package com.example.mcs.mmm_project.pojo;

import com.example.mcs.mmm_project.helper.StringHelper;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;


public class Event implements Serializable {
    private String firebaseIndex;

    @DatabaseField
    public String adresse;
    @DatabaseField
    public int age_maximum;
    @DatabaseField
    public int age_minimum;
    @DatabaseField
    public String ages;
    @DatabaseField
    public String apercu;
    @DatabaseField
    public String animateurs;
    @DatabaseField
    public String code_postal;
    @DatabaseField
    public String date_debut;
    @DatabaseField
    public String date_debut_jour;
    @DatabaseField
    public String date_debut_mois;
    @DatabaseField
    public String date_fin;
//    public String date_fin_jour;
//    public String date_fin_mois;
//    public String dates;
    @DatabaseField
    public String departement;
//    public String derniere_date;
//    public String derniere_fermeture;
//    public String derniere_mise_a_jour;
//    public String derniere_ouverture;
    @DatabaseField
    public String description_fr;
    @DatabaseField
    public String description_longue_fr;
//    public String description_longue_html_fr;
    @DatabaseField
    public String detail_des_conditions_fr;
    public Object geolocalisation;
    @DatabaseField
    public String horaires_detailles_fr;
//    public String horaires_iso;
    @DatabaseField(id = true)
    public String identifiant;
//    public String identifiant_du_lieu;
    @DatabaseField
    public String image;
    @DatabaseField
    public String image_source;
    @DatabaseField
    public String inscription_necessaire;
    @DatabaseField
    public String lien;
    @DatabaseField
    public String lien_d_inscription;
    public int nb_evenements;
    @DatabaseField
    public String nom_du_lieu;
    @DatabaseField
    public String organisateur;
    @DatabaseField
    public String pays;
    @DatabaseField
    public String publics_concernes;
    @DatabaseField
    public String region;
    @DatabaseField
    public String resume_dates_fr;
    @DatabaseField
    public String resume_horaires_fr;
    @DatabaseField
    public String selection;
    @DatabaseField
    public String site_web_du_lieu;
    @DatabaseField
    public String statut;
    @DatabaseField
    public String telephone_du_lieu;
    @DatabaseField
    public String thematiques;
//    public String image_source;
//    public String inscription_necessaire;
//    public String lien;
//    public String lien_d_inscription;
//    public int nb_evenements;
//    public String nom_du_lieu;
//    public String organisateur;
//    public String pays;
//    public String publics_concernes;
//    public String region;
//    public String resume_dates_fr;
//    public String resume_horaires_fr;
//    public String selection;
//    public String statut;
//    public String telephone_du_lieu;
//    public String thematiques;
    @DatabaseField
    public String titre_fr;
    @DatabaseField
    public String tranche;
    @DatabaseField
    public String type_d_animation;
    @DatabaseField
    public String ville;
    @DatabaseField
    public int taux_remplissage;
    public Map<String, Evaluation> evaluations = new HashMap<>(); // UUID, Evaluation

//    public String type_d_animation;
//    public String ville;

    public Event() { }

    public String getFirebaseIndex() {
        return firebaseIndex;
    }

    public void setFirebaseIndex(String firebaseIndex) {
        this.firebaseIndex = firebaseIndex;
    }

    public Collection<Evaluation> getEvaluations() {
        return evaluations.values();
    }

    @Override
    public String toString() {
        return firebaseIndex + ": " + titre_fr;
    }

    public EventPosition getGeolocalisation() {
        try {
            ArrayList<Double> pos = (ArrayList<Double>) geolocalisation;
            return new EventPosition(pos.get(0), pos.get(1), this);
        }
        catch (Exception e) {
            Logger.getGlobal().warning(e.toString());
            return null;
        }
    }

    public Calendar getCalendar(String value) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy", Locale.FRANCE);
        try {
            cal.setTime(sdf.parse(value.replace("1er", "1")));
            return cal;
        } catch (ParseException e) {
            Logger.getGlobal().warning(e.toString());
            return null;
        }
    }

    public float getEvaluationAvg() {
        float avg = 0;
        int count = 0;
        for (Evaluation e: getEvaluations()) {
            if (e.rating != 0) {
                avg += e.rating;
                count++;
            }
        }

        if (count > 0) {
            return avg / count;
        }
        return 0;
    }

    public int getEvaluationStarCount() {
        int count = 0;
        for (Evaluation e: getEvaluations()) {
            if (e.rating != 0) {
                count++;
            }
        }
        return count;
    }

    public void addUserEvaluation(Evaluation evaluation) {
        evaluations.put(StringHelper.getUniqueID(), evaluation);
    }
}
