package com.snakegj.popup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.snakegj.R;
import com.snakegj.jeu.Jeu;

public class PopupTutoriel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tutoriel);

        Button btnOK= findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupTutoriel.this, Jeu.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
                finish();
            }
        });
    }

}
