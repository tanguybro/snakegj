package com.snakegj;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Renseignement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renseignement);

        final EditText champPseudo = findViewById(R.id.chpPseudo);
        Button btnValider = findViewById(R.id.btnValiderPseudo);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(champPseudo.getText().toString().isEmpty())
                    Toast.makeText(Renseignement.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else if(contientCaracSpeciaux(champPseudo.getText().toString()))
                    Toast.makeText(Renseignement.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Renseignement.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pseudo", champPseudo.getText().toString());
                    editor.commit();
                    finish();
                }
            }
        });
    }

    private boolean contientCaracSpeciaux(String string) {
        return Pattern.compile("[@#$%*^¨&+-=()_<>.,;!?/]").matcher(string).find();
    }

}
