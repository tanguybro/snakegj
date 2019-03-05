package com.snakegj.popup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snakegj.Jeu;
import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.snake.JeuVue;

public class PopupFinPartie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_fin_partie);

        TextView score = findViewById(R.id.scoreFinal);
        Button btnRejouer = findViewById(R.id.btnRejouer);
        Button btnQuitter = findViewById(R.id.btnQuitter);


        /** probleme ne trouve pas le pseudo et peut pas ecrire dans firebase */
        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupFinPartie.this, Jeu.class);
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

        score.setText("Votre score : " + JeuVue.getScore());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int largeur = dm.widthPixels;
        int hauteur = dm.heightPixels;

        getWindow().setLayout((int) (largeur * 0.6), (int) (hauteur * 0.6));
    }
}
