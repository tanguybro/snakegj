package com.snakegj.popup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.snakegj.CurrentUser;
import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.Score;
import com.snakegj.jeu.Jeu;

public class PopupFinPartie extends AppCompatActivity {

    private TextView descFinPartie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_fin_partie);

        TextView txtScore = findViewById(R.id.scoreFinal);
        Button btnRejouer = findViewById(R.id.btnRejouer);
        Button btnQuitter = findViewById(R.id.btnQuitter);
        descFinPartie = findViewById(R.id.descFinPart);
        final int score = getIntent().getIntExtra("score", 0);

        txtScore.setText("VOTRE SCORE : " + score);
        Score.inscrireScoreSiDansClassement(score, descFinPartie);
        if(CurrentUser.getMeilleurScore() > score) CurrentUser.setMeilleurScore(score);

        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopupFinPartie.this, Jeu.class));
                finish();
            }
        });

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopupFinPartie.this, Menu.class));
                finish();
            }
        });

    }

}
