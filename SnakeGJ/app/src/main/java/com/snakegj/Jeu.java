package com.snakegj;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class Jeu extends AppCompatActivity {

    private JeuVue jeuVue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jeuVue = new JeuVue(this);
        setContentView(jeuVue);

    }



}
