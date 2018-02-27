package com.example.mcs.mmm_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.mcs.mmm_project.adapter.SQLDatabaseHelper;
import com.example.mcs.mmm_project.pojo.Event;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.progressBar) public ProgressBar progressBar;
    SQLDatabaseHelper sqlDatabaseHelper;
    long countOf = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        sqlDatabaseHelper = new SQLDatabaseHelper(getApplicationContext());
        try {
            countOf = sqlDatabaseHelper.getDao(Event.class).countOf();
        } catch (SQLException e) {
            Log.e("Loading exception", e.getMessage());
        }

        Log.i("Load", "Succes");

        new Thread(() -> {
            waitUntilLoad();
            startApp();
            finish();
        }
        ).start();
    }

    private void waitUntilLoad() {
        if(countOf <=0){
            while(sqlDatabaseHelper.getNbEvents() < 5000){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void startApp() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }
}
