package com.snakegj;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Collections;

public class Classement extends AppCompatActivity {
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classement);

        table = findViewById(R.id.tableScores);
        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(estConnecteAInternet())
            afficherClassement();
        else {
            TextView t = findViewById(R.id.msgErreur);
            t.setText("Vous n'êtes pas connecté à Internet");
        }
    }

    private void afficherClassement() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        TableRow entete = (TableRow) getLayoutInflater().inflate(R.layout.classement_entete, null);
        table.addView(entete);

        Query q = database.orderByValue().limitToLast(10);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<View> vues = new ArrayList<>();
                int i = 10;
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    TableRow ligne = (TableRow) getLayoutInflater().inflate(R.layout.classement_ligne, table, false);
                    TextView pseudo = ligne.findViewById(R.id.pseudo);
                    TextView score = ligne.findViewById(R.id.score);
                    TextView top = ligne.findViewById(R.id.top);
                    pseudo.setText(d.getKey());
                    score.setText(String.valueOf(d.getValue()));
                    top.setText(String.valueOf(i));
                    vues.add(ligne);
                    i--;
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
