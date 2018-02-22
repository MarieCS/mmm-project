package com.example.mcs.mmm_project.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcs.mmm_project.R;
import com.example.mcs.mmm_project.adapter.SQLDatabaseHelper;
import com.example.mcs.mmm_project.pojo.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcs on 19/02/18.
 */

public class EventSearch extends Fragment {

    @BindView(R.id.lieu) TextView lieu;
    @BindView(R.id.theme) TextView theme;
    @BindView(R.id.mot_clef) TextView mot_clef;
    @BindView(R.id.submit) Button submit;
    SQLDatabaseHelper sqlDatabaseHelper;

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
//        try {
//            System.out.println("toto>"+sqlDatabaseHelper.getEventPojoDao().queryForAll().size()+"");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        if(mot_clef.getText() != null && mot_clef.getText() != ""){
//            mDatabase.value
//            if(issue.child("fields").child("ville").exists()
//                    && issue.child("fields").child("ville").getValue().equals(city)){
//
//            }
        }
//        mDatabase.orderByKey().limitToFirst(50);
    }
}
