package com.snakegj.jeu;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

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
        txtScore = new TextView(this);
        txtScore.setText("SCORE : 0");
        txtScore.setTextSize(20);
        txtScore.setGravity(Gravity.START);
        txtScore.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        Typeface typeS = Typeface.createFromAsset(getAssets(),"fonts/alarm_clock.ttf");
        txtScore.setTypeface(typeS);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(txtScore);
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
