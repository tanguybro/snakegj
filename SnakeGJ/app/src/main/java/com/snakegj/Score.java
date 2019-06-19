package com.snakegj;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Score {

    private static void inscrireClassement(int score, DataSnapshot d) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Scores");
        if(d == null || d.getValue(Integer.class) < score)
            database.child(CurrentUser.getPseudo()).setValue(score);
    }

    public static void inscrireScoreSiDansClassement(final int score, final TextView descFinPartie) {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Scores");
        Query q = database.orderByValue().limitToLast(10);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                int total = (int) dataSnapshot.getChildrenCount();
                DataSnapshot pseudo = null;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(CurrentUser.getPseudo()))
                        pseudo = ds;
                }
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    if(score >= d.getValue(Integer.class)) {
                        int position = total - i;
                        String nomPos = "EME";
                        if(position == 1) nomPos = "ER";
                        if(descFinPartie != null)
                            descFinPartie.setText("BRAVO MANIFESTANT ! VOUS ETES " + position + nomPos);
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
