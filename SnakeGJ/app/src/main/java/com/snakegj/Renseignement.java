package com.snakegj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import static com.google.ads.consent.ConsentStatus.NON_PERSONALIZED;
import static com.google.ads.consent.ConsentStatus.PERSONALIZED;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class Renseignement extends AppCompatActivity {

    private EditText champPseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renseignement);

        champPseudo = findViewById(R.id.chpPseudo);
        Button btnValider = findViewById(R.id.btnValiderPseudo);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(champPseudo.getText().toString().isEmpty())
                    Toast.makeText(Renseignement.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else if(contientCaracSpeciaux(champPseudo.getText().toString()))
                    Toast.makeText(Renseignement.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();
                else {
                    CurrentUser.setPseudo(champPseudo.getText().toString());
                    startActivity(new Intent(Renseignement.this, Menu.class));
                    finish();
                }
            }
        });
    }

    private boolean contientCaracSpeciaux(String string) {
        return Pattern.compile("[@#$%*^¨&+-=()_<>.,;!?/]").matcher(string).find();
    }


}
