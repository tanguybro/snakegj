package com.snakegj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.snakegj.chargement.ChargementJeu;

public class ModeJeu extends AppCompatActivity {

    private ImageButton btnGJ;
    private ImageButton btnPl;
    private ImageButton btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_jeu);

        btnGJ = findViewById(R.id.image_giletJaune);
        btnPl = findViewById(R.id.image_giletPolicier);
        btnRetour = findViewById(R.id.btnRetour);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeJeu.this, ChargementJeu.class);
                intent.putExtra("mode", "révolution");
                startActivity(intent);
                overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
            }
        });

        btnPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeJeu.this, ChargementJeu.class);
                intent.putExtra("mode", "dissolution");
                startActivity(intent);
                overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
            }
        });

    }
}
