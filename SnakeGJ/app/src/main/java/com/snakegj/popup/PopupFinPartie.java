package com.snakegj.popup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.jeu.Jeu;

import java.util.regex.Pattern;

public class PopupFinPartie extends AppCompatActivity {

    private EditText champPseudo;
    private TextView descFinPartie;
    private TextView txtScore;
    private Button btnRejouer;
    private Button btnQuitter;
    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_fin_partie);

        champPseudo = findViewById(R.id.chpPseudo);
        txtScore = findViewById(R.id.scoreFinal);
        btnRejouer = findViewById(R.id.btnRejouer);
        btnQuitter = findViewById(R.id.btnQuitter);
        btnValider = findViewById(R.id.btnValider);
        descFinPartie = findViewById(R.id.descFinPart);
        final int score = getIntent().getIntExtra("score", 0);

        txtScore.setText("VOTRE SCORE : " + score);

        verifierScore(score);
        inscrireMeilleurScore(score);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(champPseudo.getText().toString().isEmpty())
                    Toast.makeText(PopupFinPartie.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else if(contientCaracSpeciaux(champPseudo.getText().toString()))
                    Toast.makeText(PopupFinPartie.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();
                else {
                    inscrireClassement(score);
                    startActivity(new Intent(PopupFinPartie.this, Menu.class));
                    finish();
                }
            }
        });



        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupFinPartie.this, Jeu.class);
                String pseudo = getIntent().getStringExtra("pseudo");
                intent.putExtra("pseudo", pseudo);
                startActivity(intent);
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

    private boolean contientCaracSpeciaux(String string) {
        return Pattern.compile("[@#$%*^¨&+-=()_<>.,;!?/]").matcher(string).find();
    }

    private void inscrireMeilleurScore(int score) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(score > preferences.getInt("Meilleur Score", 0)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Meilleur Score", score);
            editor.commit();
        }
    }

    private void inscrireClassement(int score) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        database.child(champPseudo.getText().toString()).setValue(score);
    }

    //redondance avec classement à revoir
    private void verifierScore(final int score) {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        Query q = database.orderByValue().limitToLast(10);
        Query q2 = database.orderByValue().endAt(10).limitToFirst(1);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                int total = (int) dataSnapshot.getChildrenCount();
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    if(score > d.getValue(Integer.class)) {
                        int position = total - i;
                        descFinPartie.setText("BRAVO MANIFESTANT ! VOUS ETES " + position + "EME" );
                        champPseudo.setVisibility(View.VISIBLE);
                        btnValider.setVisibility(View.VISIBLE);
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
