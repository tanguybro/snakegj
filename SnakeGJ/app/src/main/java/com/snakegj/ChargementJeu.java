package com.snakegj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.snakegj.jeu.Jeu;
import com.snakegj.popup.PopupTutoriel;

public class ChargementJeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement_jeu);

        ImageView logo1 = findViewById(R.id.logoCharg1);
        ImageView logo2 = findViewById(R.id.logoCharg2);
        ImageView logo3 = findViewById(R.id.logoCharg3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ChargementJeu.this);
                if (preferences.getBoolean("firstLaunch", true)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("firstLaunch", false);
                    editor.apply();
                    startActivity(new Intent(ChargementJeu.this, PopupTutoriel.class));
                }
                else
                    startActivity(new Intent(ChargementJeu.this, Jeu.class));
                finish();
            }
        }, 4000);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.move_up_down);
        logo1.startAnimation(animation);
        logo2.startAnimation(animation);
        logo3.startAnimation(animation);
    }
}
