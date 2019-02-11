package com.snakegj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snakegj.snake.JeuVue;


public class Jeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new JeuVue(this));
    }



}
