package com.snakegj.popup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.snakegj.jeu.Jeu;
import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.jeu.JeuVue;

import java.util.regex.Pattern;

public class PopupFinPartie extends AppCompatActivity {

    private EditText champPseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_fin_partie);

        champPseudo = findViewById(R.id.chpPseudo);
        TextView txtScore = findViewById(R.id.scoreFinal);
        Button btnRejouer = findViewById(R.id.btnRejouer);
        Button btnQuitter = findViewById(R.id.btnQuitter);
        int score = getIntent().getIntExtra("score", 0);

        txtScore.setText("Votre score : " + score);


        // a finir
        if(champPseudo.getText().toString().isEmpty())
            Toast.makeText(PopupFinPartie.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
        else if(contientCaracSpeciaux(champPseudo.getText().toString()))
            Toast.makeText(PopupFinPartie.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();


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
        getWindow().setLayout((int) (dm.widthPixels * 0.6), (int) (dm.heightPixels * 0.6));
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

}
