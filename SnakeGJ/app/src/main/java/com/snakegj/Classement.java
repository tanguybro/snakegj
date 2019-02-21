package com.snakegj;

import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Classement extends AppCompatActivity {

    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classement);

        table = (TableLayout) findViewById(R.id.tableScores);

        FirebaseApp.initializeApp(this);
        finPartie();

    }

    public void afficherClassement() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int valeur = 0;
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    if(valeur < (Integer) d.getValue()) {
                        valeur = ((Integer) d.getValue()).intValue();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
    }

    public void finPartie() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        database.child("Axel").setValue(23);
        database.child("Lucien").setValue(50);
    }
}
