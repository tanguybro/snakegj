package com.snakegj.pages;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.snakegj.Menu;
import com.snakegj.R;

import java.io.IOException;

public class Options extends AppCompatActivity {
    private Button btnJouer;
    private ImageButton btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        btnRetour = findViewById(R.id.btnRetour);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnJouer = findViewById(R.id.btnSon);
        if(!Menu.getPlayer().isPlaying())
            btnJouer.setText("Off");

        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnJouer.getText().equals("On")) {
                    Menu.getPlayer().stop();
                    btnJouer.setText("Off");
                    sauvegarderEtatMusique();
                }
                else {
                    try {
                        Menu.getPlayer().prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Menu.getPlayer().seekTo(0);
                    Menu.getPlayer().start();
                    btnJouer.setText("On");
                    sauvegarderEtatMusique();
                }
            }
        });
    }

    public void sauvegarderEtatMusique() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("etat musique", btnJouer.getText().toString());
        editor.commit();
    }
}
