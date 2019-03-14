package com.snakegj.pages;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.snakegj.Menu;
import com.snakegj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Classement extends AppCompatActivity {
    private TableLayout table;
    private TableRow entete;
    private ImageButton btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classement);

        btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        table = findViewById(R.id.tableScores);
        entete = (TableRow) getLayoutInflater().inflate(R.layout.tableau_entete, null);
        if(estConnecteAInternet())
            afficherClassement();
        else {
            TextView t = findViewById(R.id.msgErreur);
            t.setText("Vous n'êtes pas connecté à Internet");
        }
    }

    private void afficherClassement() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        table.addView(entete);

        Query q = database.orderByValue().limitToLast(8); //on trie dans l'ordre croissant et on recupere les 10 derniers scores
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<View> vues = new ArrayList<>();
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    TableRow ligne = (TableRow) getLayoutInflater().inflate(R.layout.tableau_ligne, table, false);
                    TextView pseudo = ligne.findViewById(R.id.pseudo);
                    TextView score = ligne.findViewById(R.id.score);
                    pseudo.setText(d.getKey());
                    score.setText(String.valueOf(d.getValue()));
                    vues.add(ligne);
                }
                Collections.reverse(vues);

                for(View v : vues)
                    table.addView(v);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private boolean estConnecteAInternet() {
        NetworkInfo network = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return !(network == null || !network.isConnected());
    }

}
