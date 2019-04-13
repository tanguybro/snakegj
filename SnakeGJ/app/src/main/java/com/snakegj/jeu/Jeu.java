package com.snakegj.jeu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;
import com.snakegj.R;
import com.snakegj.popup.PopupPause;

import java.io.IOException;


public class Jeu extends AppCompatActivity {
    private TextView txtScore;
    private TextView meilleurScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScores();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(new JeuVue(this, this));
    }

    public void modifScore(final String texteScore){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtScore.setText(texteScore);
            }
        });
    }

    private void initScores() {
        txtScore = new TextView(this);
        txtScore.setText("SCORE : 0");
        txtScore.setTextSize(20);
        txtScore.setGravity(Gravity.RIGHT);
        Typeface typeS = Typeface.createFromAsset(getAssets(),"fonts/alarm_clock.ttf");
        txtScore.setTypeface(typeS);
        meilleurScore = new TextView(this);
        int bestScore = PreferenceManager.getDefaultSharedPreferences(this).getInt("Meilleur Score", 0);
        meilleurScore.setText("MEILLEUR SCORE : " + bestScore);
        meilleurScore.setTextSize(20);
        meilleurScore.setGravity(Gravity.LEFT);
        Typeface typeMs = Typeface.createFromAsset(getAssets(),"fonts/alarm_clock.ttf");
        meilleurScore.setTypeface(typeMs);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(meilleurScore);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(Menu.NONE, Menu.NONE, 2, "score").setActionView(txtScore).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_pause) {
            startActivity(new Intent(Jeu.this, PopupPause.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
