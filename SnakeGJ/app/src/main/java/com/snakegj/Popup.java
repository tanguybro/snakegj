package com.snakegj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        EditText pseudo = findViewById(R.id.inputPseudo);
        Button btnOk = findViewById(R.id.btnOk);

        Intent intent = new Intent(this, Popup.class);
        intent.putExtra("Pseudo", pseudo.getText().toString());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int largeur = dm.widthPixels;
        int hauteur = dm.heightPixels;

        getWindow().setLayout((int) (largeur * 0.6), (int) (hauteur * 0.6));
    }
}
