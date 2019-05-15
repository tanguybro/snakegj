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
import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.jeu.Jeu;

public class PopupFinPartie extends AppCompatActivity {

    private TextView descFinPartie;
    private TextView txtScore;
    private Button btnRejouer;
    private Button btnQuitter;
    private SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_fin_partie);

        txtScore = findViewById(R.id.scoreFinal);
        btnRejouer = findViewById(R.id.btnRejouer);
        btnQuitter = findViewById(R.id.btnQuitter);
        descFinPartie = findViewById(R.id.descFinPart);
        final int score = getIntent().getIntExtra("score", 0);

        txtScore.setText("VOTRE SCORE : " + score);
        inscrireScoreSiDansClassement(score);
        inscrireMeilleurScoreSiSuperieur(score);

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
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.8), (int) (dm.heightPixels * 0.8));
    }

    private void inscrireMeilleurScoreSiSuperieur(int score) {
        if(score > preferences.getInt("Meilleur Score", 0)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Meilleur Score", score);
            editor.commit();
        }
    }

    private void inscrireClassement(int score) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        database.child(preferences.getString("pseudo","Anonyme")).setValue(score);
    }

    private void inscrireScoreSiDansClassement(final int score) {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        Query q = database.orderByValue().limitToLast(10);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                int total = (int) dataSnapshot.getChildrenCount();
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    if(score > d.getValue(Integer.class)) {
                        int position = total - i;
                        descFinPartie.setText("BRAVO MANIFESTANT ! VOUS ETES " + position + "EME" );
                        inscrireClassement(score);
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
