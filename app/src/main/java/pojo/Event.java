package pojo;

public class Event {
    public String adresse;
    public int age_maximum;
    public int age_minimum;
    public String ages;
    public String animateurs;
    public String code_postal;
    public String date_debut;
    public String date_debut_jour;
    public String date_debut_mois;
    public String date_fin;
    public String date_fin_jour;
    public String date_fin_mois;
    public String dates;
    public String departement;
    public String derniere_date;
    public String derniere_fermeture;
    public String derniere_mise_a_jour;
    public String derniere_ouverture;
    public String description_fr;
    public String description_longue_fr;
    public String description_longue_html_fr;
    public String detail_des_conditions_fr;
    public Object geolocalisation;
    public String horaires_detailles_fr;
    public String horaires_iso;
    public String identifiant;
    public String identifiant_du_lieu;
    public String inscription_necessaire;
    public String lien;
    public String lien_d_inscription;
    public int nb_evenements;
    public String nom_du_lieu;
    public String organisateur;
    public String pays;
    public String publics_concernes;
    public String region;
    public String resume_dates_fr;
    public String resume_horaires_fr;
    public String selection;
    public String statut;
    public String telephone_du_lieu;
    public String thematiques;
    public String titre_fr;
    public String tranche;
    public String type_d_animation;
    public String ville;

    public Event() {

    }

    @Override
    public String toString() {
        return "Event{" +
                "adresse='" + adresse + '\'' +
                ", titre_fr='" + titre_fr + '\'' +
                ", identifiant='" + identifiant + '\'' +
                ", identifiant_du_lieu='" + identifiant_du_lieu + '\'' +
                '}';
    }

}
