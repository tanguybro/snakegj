package com.snakegj.popup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snakegj.R;

import java.util.regex.Pattern;

public class PopupPseudo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_pseudo);

        final EditText champPseudo = findViewById(R.id.chpPseudo);
        Button btnValider = findViewById(R.id.btnValiderPseudo);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(champPseudo.getText().toString().isEmpty())
                    Toast.makeText(PopupPseudo.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else if(contientCaracSpeciaux(champPseudo.getText().toString()))
                    Toast.makeText(PopupPseudo.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PopupPseudo.this);
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
