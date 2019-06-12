package com.snakegj.chargement;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.snakegj.CurrentUser;
import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.Renseignement;

public class ChargementApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement_app);

        ImageView logo = findViewById(R.id.logo);
        CurrentUser.init(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(CurrentUser.getPseudo().equals(""))
                    startActivity(new Intent(ChargementApp.this, Renseignement.class));
                else
                    startActivity(new Intent(ChargementApp.this, Menu.class));
                finish();
            }
        }, 4000);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fondu);
        logo.startAnimation(anim);

    }
}
