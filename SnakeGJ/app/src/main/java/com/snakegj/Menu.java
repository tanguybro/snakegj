package com.snakegj;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.snakegj.jeu.Jeu;
import com.snakegj.popup.PopupPseudo;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        FirebaseApp.initializeApp(this);
        Button btnJouer = findViewById(R.id.jouer);
        Button btnClassement = findViewById(R.id.classement);

        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Jeu.class));
            }
        });

        btnClassement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Classement.class));
            }
        });

        if(PreferenceManager.getDefaultSharedPreferences(this).getString("pseudo", "").equals(""))
            startActivity(new Intent(this, PopupPseudo.class));
    }

}
