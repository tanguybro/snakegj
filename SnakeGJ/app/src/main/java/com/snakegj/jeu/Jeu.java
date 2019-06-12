package com.snakegj.jeu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.snakegj.CurrentUser;
import com.snakegj.R;
import com.snakegj.popup.PopupPause;


public class Jeu extends AppCompatActivity {
    private TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScores();
        Drawable toolbarDesign = getResources().getDrawable(R.drawable.tableau);
        getSupportActionBar().setBackgroundDrawable(toolbarDesign);
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
        View view = LayoutInflater.from(this).inflate(R.layout.jeu, null);
        txtScore = view.findViewById(R.id.score);
        TextView txtBestScore = view.findViewById(R.id.meilleurScore);
        txtBestScore.setText("RECORD : " + CurrentUser.getMeilleurScore());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
