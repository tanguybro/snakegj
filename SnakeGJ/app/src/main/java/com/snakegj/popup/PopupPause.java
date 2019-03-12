package com.snakegj.popup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.snakegj.jeu.Jeu;
import com.snakegj.Menu;
import com.snakegj.R;

public class PopupPause extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_pause);

        Button btnRejouer = findViewById(R.id.btnRejouer);
        Button btnQuitter = findViewById(R.id.btnQuitter);
        Button btnReprendre = findViewById(R.id.btnReprendre);

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

        btnReprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.6), (int) (dm.heightPixels * 0.6));
    }

}
