package com.snakegj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.snakegj.snake.JeuVue;


public class Jeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String nom;
        if(intent.getStringExtra("pseudo") == null)
            nom = intent.getStringExtra("pseudoFB");
        else
            nom = intent.getStringExtra("pseudo");
        setContentView(new JeuVue(this, nom));
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pause, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_pause) {
            Intent intent = new Intent(Jeu.this, PopupPause.class);
            startActivity(intent);
            onPause();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
