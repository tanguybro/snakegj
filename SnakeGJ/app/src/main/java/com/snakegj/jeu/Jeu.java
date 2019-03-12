package com.snakegj.jeu;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.snakegj.R;
import com.snakegj.popup.PopupPause;


public class Jeu extends AppCompatActivity {
    private TextView txtScore;
    private TextView meilleurScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScores();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(new JeuVue(this, this, getIntent().getStringExtra("pseudo")));
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
        txtScore.setText("Score : 0");
        txtScore.setTextSize(20);
        meilleurScore = new TextView(this);
        int bestScore = PreferenceManager.getDefaultSharedPreferences(this).getInt("Meilleur Score", 0);
        meilleurScore.setText("MeilleurScore : " + bestScore);
        meilleurScore.setTextSize(20);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(Menu.NONE, Menu.NONE, 1, "meilleurScore").setActionView(meilleurScore).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, 2, "score").setActionView(txtScore).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_pause) {
            Intent intent = new Intent(Jeu.this, PopupPause.class);
            startActivity(intent);
            onPause();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}