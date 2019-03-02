package com.snakegj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snakegj.snake.JeuVue;

public class PopupPause extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_fin_partie);

        TextView score = findViewById(R.id.scoreFinal);
        Button btnRejouer = findViewById(R.id.btnRejouer);
        Button btnQuitter = findViewById(R.id.btnQuitter);


        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopupPause.this, Jeu.class));
                finish();
            }
        });

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopupPause.this, Menu.class));
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
