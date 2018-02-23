package com.example.mcs.mmm_project.fragment;

import android.accounts.Account;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.adapter.SQLDatabaseHelper;
import com.example.mcs.mmm_project.pojo.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcs on 19/02/18.
 */

public class EventSearch extends Fragment {

    @BindView(R.id.lieu) EditText lieu;
    @BindView(R.id.theme) EditText theme;
    @BindView(R.id.mot_clef) EditText mot_clef;
    @BindView(R.id.submit) Button submit;
    SQLDatabaseHelper sqlDatabaseHelper;

    OnSearchEnd listener;

    public void setListener(OnSearchEnd listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_search, container, false);
        ButterKnife.bind(this, view);
//        searchView.setQueryHint("Titre de l'évènement");
//        searchView.onActionViewExpanded();
//        searchView.setIconified(false);
//        searchView.clearFocus();

        sqlDatabaseHelper = new SQLDatabaseHelper(getContext());

        System.out.println(sqlDatabaseHelper.getEventPojoDao());

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search();
            }
        });

        return view;
    }

    private void search(){
        try {
            QueryBuilder<Event, ?> queryBuilder = sqlDatabaseHelper.getDao(Event.class).queryBuilder();
            Where<Event, ?> where = queryBuilder.where();
            int nbWhereArgs = 0;
            SelectArg selectArg = new SelectArg();
            // define our query as 'name = ?'
    //                where.eq("name", selectArg);

            if(lieu.getText() != null && !lieu.getText().toString().equals("")){
                where.like("ville", lieu.getText());
                nbWhereArgs++;
            }
            if(theme.getText() != null && !theme.getText().toString().equals("")){
                where.like("thematiques", theme.getText());
                nbWhereArgs++;
            }
            if(mot_clef.getText() != null && !mot_clef.getText().toString().equals("")){
                where.like("titre_fr", mot_clef.getText());
                nbWhereArgs++;
            }


            if(nbWhereArgs > 0){
                where.and(nbWhereArgs);
                // prepare it so it is ready for later query or iterator calls
                PreparedQuery<Event> preparedQuery = queryBuilder.prepare();

                List<Event> events = sqlDatabaseHelper.getDao(Event.class).query(preparedQuery);

                System.out.println("nb res >" + events.size());
                if(listener != null){
                    listener.onSearchEnd(events);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
