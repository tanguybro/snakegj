package com.snakegj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        FirebaseApp.initializeApp(this);
        Button btnJouer = findViewById(R.id.jouer);
        Button btnClassement = findViewById(R.id.classement);
        TextView pseudoMenu = findViewById(R.id.pseudo);
        pseudoMenu.setText("Pseudo : " + CurrentUser.getPseudo());

        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ModeJeu.class));
            }
        });

        btnClassement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Classement.class));
            }
        });

    }

}
