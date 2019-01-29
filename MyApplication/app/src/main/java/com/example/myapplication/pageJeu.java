package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class pageJeu extends AppCompatActivity {

    private TextView points;
    private ImageView macron;
    private ImageView piece;
    private int clic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_jeu);

        this.points = (TextView) findViewById(R.id.points);
        this.macron = (ImageView) findViewById(R.id.macron);
        this.piece = (ImageView) findViewById(R.id.piece);

        macron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementerScore();
                animationPiece();
            }
        });
    }

    public void incrementerScore() {
        clic++;
        points.setText("Score : " + clic);
    }

    public void animationPiece() {
        piece.setVisibility(View.VISIBLE);
        macron.postDelayed(new Runnable() {
            public void run() {
                piece.setVisibility(View.INVISIBLE);
            }
        }, 3500);
        ObjectAnimator.ofFloat(piece, "translationY", 0, -50).setDuration(500).start();
    }
}
