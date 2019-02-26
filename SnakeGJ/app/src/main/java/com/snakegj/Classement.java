package com.snakegj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Classement extends AppCompatActivity {

    private TableLayout table;
    private TableRow entete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classement);

        table = findViewById(R.id.tableScores);
        entete = (TableRow) getLayoutInflater().inflate(R.layout.tableau_entete, null);
        afficherClassement();
    }

    public void afficherClassement() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        table.addView(entete);

        //on trie dans l'ordre croissant et on recupere les 10 derniers scores
        Query q = database.orderByValue().limitToLast(10);
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

}
