package com.snakegj;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Score {

    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static int getMeilleurScore() {
        return preferences.getInt("Meilleur Score", 0);
    }

    public static void inscrireMeilleurScore(int score) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Meilleur Score", score);
        editor.apply();
    }

    public static void inscrireDansClassement(int score, String pseudo) {

    }

    private static boolean estDansClassement(int score) {
        return true; // A CODER
    }

    private static void inscrireClassement(int score, DataSnapshot d) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        if(d == null || d.getValue(Integer.class) < score)
            database.child(preferences.getString("pseudo","Anonyme")).setValue(score);
    }

    // provisoire
    public static void inscrireScoreSiDansClassement(final int score, final TextView descFinPartie) {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        Query q = database.orderByValue();

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                int total = (int) dataSnapshot.getChildrenCount();
                DataSnapshot pseudo = null;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(preferences.getString("pseudo","Anonyme")))
                        pseudo = ds;
                }
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    if(score >= d.getValue(Integer.class)) {
                        int position = total - i;
                        descFinPartie.setText("BRAVO MANIFESTANT ! VOUS ETES " + position + "EME" );
                        inscrireClassement(score, pseudo);
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
