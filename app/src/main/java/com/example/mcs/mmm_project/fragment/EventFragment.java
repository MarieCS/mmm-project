package com.example.mcs.mmm_project.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.adapter.RecyclerViewAdapter_EvaluationList;
import com.example.mcs.mmm_project.helper.DateHelper;
import com.example.mcs.mmm_project.helper.FirebaseHelper;
import com.example.mcs.mmm_project.helper.IntentHelper;
import com.example.mcs.mmm_project.helper.StringHelper;
import com.example.mcs.mmm_project.pojo.Evaluation;
import com.example.mcs.mmm_project.pojo.Event;
import com.example.mcs.mmm_project.pojo.EventPosition;
import com.example.mcs.mmm_project.pojo.Parcours;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class EventFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.title_fr) TextView title_fr;
    @BindView(R.id.adresse) TextView adresse;
    @BindView(R.id.description_fr) TextView description_fr;
    @BindView(R.id.infos) TextView infos;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.mapView) MapView mapView;
    @BindView(R.id.event_tabHost) TabHost tabHost;
    @BindView(R.id.ajoutAuParcours) Button ajoutAuParcours;
    @BindView(R.id.enleverDuParcours) Button enleverDuParcours;
    @BindView(R.id.event_infos_buttons) LinearLayout event_infos_buttons;
    @BindView(R.id.event_seekbar_remplissage) SeekBar event_seekbar_remplissage;
    @BindView(R.id.event_seekbar_label_remplissage) TextView event_seekbar_label_remplissage;
    @BindView(R.id.event_taux_remplissage) TextView event_taux_remplissage;

    // Evaluation
    @BindView(R.id.event_user_ratingLabel) TextView event_user_ratingLabel;
    @BindView(R.id.event_user_ratingBar) RatingBar event_user_ratingBar;
    @BindView(R.id.event_users_ratingLabel) TextView event_users_ratingLabel;
    @BindView(R.id.event_users_RatingBar) RatingBar event_users_RatingBar;
    @BindView(R.id.event_users_RecyclerView) RecyclerView event_users_RecyclerView;
    @BindView(R.id.event_user_btnSendEval) Button event_user_btnSendEval;
    @BindView(R.id.event_user_comment) EditText event_user_comment;

    private static final String ARG_EVENT = "event";
    private Event event;
    private Unbinder unbinder;
    private GoogleMap map;
    private Integer tauxRemplissageInitial;
    private Integer tauxRemplissageCourant;
    private ArrayList<Evaluation> usersEvaluations = new ArrayList<>();

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     */
    public static EventFragment newInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_fragment, container, false);
        this.unbinder = ButterKnife.bind(this, view);
        tabHost.setup();
        TabHost.TabSpec mSpec = tabHost.newTabSpec("Evenement");
        mSpec.setContent(R.id.event_tab1);
        mSpec.setIndicator("Evenement");
        tabHost.addTab(mSpec);

        mSpec = tabHost.newTabSpec("Evaluation");
        mSpec.setContent(R.id.event_tab2);
        mSpec.setIndicator("Evaluation");
        tabHost.addTab(mSpec);

        mSpec = tabHost.newTabSpec("Organisation");
        mSpec.setContent(R.id.event_tab3);
        mSpec.setIndicator("Organisation");
        tabHost.addTab(mSpec);


        if (getArguments() != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);

            event = (Event) getArguments().getSerializable(ARG_EVENT);

            Picasso.with(image.getContext()).load(event.image).centerCrop().fit().into(image);

            //////////// TEXTES
            title_fr.setText(event.titre_fr);
            adresse.setText(event.nom_du_lieu + "\n" + event.adresse + " (" + event.departement + ")");

            if (StringHelper.notEmpty(event.description_fr)) {
                description_fr.setText(event.description_fr);
            }

            StringBuilder strbInfos = new StringBuilder();
            if (!event.date_debut.equalsIgnoreCase(event.date_fin)) {
                strbInfos.append("Du " + DateHelper.toDate(event.getStartDatetime(), "dd MMMM") + " au " + DateHelper.toDate(event.getEndDatetime(), "dd MMMM yyyy") + "\n");
            }
            if (StringHelper.notEmpty(event.horaires_iso)) {
                strbInfos.append("Horaires :\n");
                for (String isoDay: event.horaires_iso.split("\n")) { // Par jour
                    Calendar ouverture = DateHelper.getFrom(isoDay.substring(0, DateHelper.ISO8601_LENGTH));
                    Calendar fermeture = DateHelper.getFrom(isoDay.substring(DateHelper.ISO8601_LENGTH+1));

                    strbInfos.append("   > " + DateHelper.toDate(ouverture, "EEEE dd MMM") + " : ");
                    strbInfos.append("de " + DateHelper.toDate(ouverture, "HH:mm") + " ");
                    strbInfos.append("à " + DateHelper.toDate(fermeture, "HH:mm") + "\n");
                }
                strbInfos.append("\n");
            }
            if (StringHelper.notEmpty(event.tranche)) {
                strbInfos.append("Ages : " + event.tranche + "\n");
            }
            if (StringHelper.notEmpty(event.type_d_animation)) {
                strbInfos.append("Type d'animation : " + event.type_d_animation + "\n");
            }
            if (StringHelper.notEmpty(event.thematiques)) {
                strbInfos.append("Thématiques : " + event.thematiques.replace("|", ", ") + "\n");
            }
            if (StringHelper.notEmpty(event.detail_des_conditions_fr)) {
                strbInfos.append("Conditions : " + event.detail_des_conditions_fr + "\n");
            }
            if (StringHelper.notEmpty(event.inscription_necessaire) && event.inscription_necessaire.equalsIgnoreCase("Oui")) {
                strbInfos.append("Inscription nécéssaire : " + event.lien_d_inscription + "\n");
            }
            infos.setText(strbInfos);
            updateLabelTauxRemplissage();



            //////////// BOUTONS
            ajoutAuParcours.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcours.getInstance().addEvent(event);
                    Toast.makeText(getContext(), "Evenement ajouté au parcours", Toast.LENGTH_LONG).show();
                    ajoutAuParcours.setVisibility(View.GONE);
                    enleverDuParcours.setVisibility(View.VISIBLE);
                }
            });

            enleverDuParcours.setVisibility(View.GONE);
            enleverDuParcours.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcours.getInstance().removeEvent(event);
                    Toast.makeText(getContext(), "Evenement enlevé du parcours", Toast.LENGTH_LONG).show();
                    ajoutAuParcours.setVisibility(View.VISIBLE);
                    enleverDuParcours.setVisibility(View.GONE);
                }
            });

            if (StringHelper.notEmpty(event.telephone_du_lieu)) {
                addInfosButton("Téléphoner").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentHelper.dialPhoneNumber(getActivity(), event.telephone_du_lieu);
                    }
                });
            }

            if (StringHelper.notEmpty(event.site_web_du_lieu)) {
                addInfosButton("Site web").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentHelper.openWebPage(getActivity(), event.site_web_du_lieu);
                    }
                });
            }

            if (StringHelper.notEmpty(event.date_debut, event.date_fin)) {
                addInfosButton("Agenda").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentHelper.addCalendarEvent(getActivity(), event);
                    }
                });
            }

            addInfosButton("Partager").setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentHelper.share(getActivity(), event);
                }
            });


            //////////////// MENU EVALUATION
            updateRatingBars();

            event_user_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    event.getUserEvaluation().rating = rating;
                    FirebaseHelper.update(event);
                    updateRatingBars();
                }
            });

            FirebaseHelper.getEvaluations(event, new ChildEventListener() {
                private int getRecyclerViewIndex(Evaluation eval) {
                    int i = 0;
                    int index = -1;
                    while (i < usersEvaluations.size() && index == -1) {
                        if (usersEvaluations.get(i).equals(eval)) {
                            index = i;
                        }
                        i++;
                    }
                    return index;
                }

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Evaluation eval = dataSnapshot.getValue(Evaluation.class);
                    eval.uuidKey = dataSnapshot.getKey();
                    event.evaluations.put(eval.uuidKey, eval);
                    if (!eval.isEmpty()) {
                        usersEvaluations.add(eval);
                        event_users_RecyclerView.setAdapter(new RecyclerViewAdapter_EvaluationList(usersEvaluations));
                    }
                    updateRatingBars();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Evaluation newEval = dataSnapshot.getValue(Evaluation.class);
                    newEval.uuidKey = dataSnapshot.getKey();
                    event.evaluations.put(newEval.uuidKey, newEval);

                    int index = getRecyclerViewIndex(newEval);
                    if (index != -1) {
                        usersEvaluations.remove(index);
                    }
                    else {
                        index = 0;
                    }

                    if (!newEval.isEmpty()) {
                        usersEvaluations.add(index, newEval);
                    }

                    event_users_RecyclerView.setAdapter(new RecyclerViewAdapter_EvaluationList(usersEvaluations));
                    updateRatingBars();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Evaluation eval = dataSnapshot.getValue(Evaluation.class);
                    eval.uuidKey = dataSnapshot.getKey();
                    event.evaluations.remove(eval.uuidKey);
                    usersEvaluations.remove(getRecyclerViewIndex(eval));
                    event_users_RecyclerView.setAdapter(new RecyclerViewAdapter_EvaluationList(usersEvaluations));
                    updateRatingBars();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            event_users_RecyclerView.setLayoutManager(llm);

            event_user_btnSendEval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event.getUserEvaluation().comment = event_user_comment.getText().toString();
                    FirebaseHelper.update(event);
                }
            });


            //////////////// MENU ORGANISATION
            event_seekbar_label_remplissage.setText(event.taux_remplissage + "%");
            event_seekbar_remplissage.setProgress(event.taux_remplissage);
            event_seekbar_remplissage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tauxRemplissageCourant = progress;
                    event_seekbar_label_remplissage.setText(progress + "%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    tauxRemplissageInitial = seekBar.getProgress();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (tauxRemplissageCourant != tauxRemplissageInitial) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        event.taux_remplissage = tauxRemplissageCourant;
                                        FirebaseHelper.update(event);
                                        updateLabelTauxRemplissage();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        event_seekbar_remplissage.setProgress(tauxRemplissageInitial);
                                        event_seekbar_label_remplissage.setText(tauxRemplissageInitial + "%");
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setMessage("Valider le nouveau taux de remplissage de " + tauxRemplissageCourant + "% ?")
                            .setPositiveButton("Oui", dialogClickListener)
                            .setNegativeButton("Non", dialogClickListener)
                            .show();
                    }
                }
            });
        }

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        EventPosition pos = event.getGeolocalisation();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos.getPosition(), 20));
        map.addMarker(new MarkerOptions()
                .position(pos.getPosition())
                .title(event.titre_fr)
                .snippet(event.adresse + " " + event.code_postal + " " + event.ville));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Button addInfosButton(String text) {
        Button button = new Button(getActivity());
        button.setText(text);
        button.setTextSize(10);
        event_infos_buttons.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return button;
    }

    private void updateRatingBars() {
        //// USER
        Evaluation userEval = event.evaluations.get(StringHelper.getUniqueID());
        if (userEval == null || userEval.rating == 0) {
            event_user_ratingLabel.setText("Vous n'avez pas noté cet événement");
            event_user_ratingBar.setRating(0);
        }
        else {
            event_user_ratingLabel.setText("Vous avez noté cet événement à " + (userEval.rating + "").substring(0,1) + "/" + event_user_ratingBar.getNumStars() + " étoiles");
            event_user_ratingBar.setRating(userEval.rating);
        }
        event_user_comment.setText(event.getUserEvaluation().comment);

        //// USERS
        String lblUsersEvaluations;
        float avg = event.getEvaluationAvg();
        if (event.getEvaluationStarCount() == 0) {
            lblUsersEvaluations = "Aucun utilisateur a noté cet événement";
        }
        else if (event.getEvaluationStarCount() == 1) {
            lblUsersEvaluations = event.getEvaluationStarCount() + " utilisateur a noté cet événement (" + (avg+"00").substring(0,3) + "/" + event_users_RatingBar.getNumStars() + ")";
        }
        else {
            lblUsersEvaluations = event.getEvaluationStarCount() + " utilisateurs ont notés cet événement (" + (avg+"00").substring(0,3) + "/" + event_users_RatingBar.getNumStars() + ")";
        }
        event_users_ratingLabel.setText(lblUsersEvaluations);
        event_users_RatingBar.setRating(avg);
    }

    private void updateLabelTauxRemplissage() {
        event_taux_remplissage.setText("Taux de remplissage " + (StringHelper.empty(event.taux_remplissage + "") ? " inconnu" : " : " + event.taux_remplissage + "%"));
    }
}
