package com.snakegj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.snakegj.snake.JeuVue;


public class Jeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String nom = intent.getStringExtra("Pseudo");
        setContentView(R.layout.jeu);
        RelativeLayout r = findViewById(R.id.affichageJeu);
        r.addView(new JeuVue(this, nom));
    }



}
