package com.snakegj.popup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.snakegj.Menu;
import com.snakegj.R;
import com.snakegj.jeu.Jeu;

public class PopupTutoriel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tutoriel);

        Button btnOK= findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopupTutoriel.this, Jeu.class));
                finish();
            }
        });
    }

}
