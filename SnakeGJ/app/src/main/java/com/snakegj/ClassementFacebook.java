package com.snakegj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassementFacebook extends Fragment {

    private TableLayout table;
    private TableRow entete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.classement_facebook, container, false);
        table = rootView.findViewById(R.id.tableScores);
        entete = (TableRow) getLayoutInflater().inflate(R.layout.tableau_entete, null);
        if(Menu.estConnecteFB())
            obtenirAmisFb(AccessToken.getCurrentAccessToken());
        afficherClassement();

        return rootView;
    }

    public void afficherClassement() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ClassementFB");
        table.addView(entete);
        final List<String> amis = obtenirAmisFb(AccessToken.getCurrentAccessToken());
        //on trie dans l'ordre croissant et on recupere les 10 derniers scores
        Query q = database.orderByValue().limitToLast(10);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<View> vues = new ArrayList<>();
                for(DataSnapshot d : dataSnapshot.getChildren()) { //parcourt les noms dans le classement fb
                    TableRow ligne = (TableRow) getLayoutInflater().inflate(R.layout.tableau_ligne, table, false);
                    TextView pseudo = ligne.findViewById(R.id.pseudo);
                    TextView score = ligne.findViewById(R.id.score);
                    if(estUnAmiFB(d.getKey(), amis) || (d.getKey().contains(Profile.getCurrentProfile().getName()))) {
                        pseudo.setText(d.getKey());
                        score.setText(String.valueOf(d.getValue()));
                    }
                    else {
                        pseudo.setText("");
                        score.setText("");
                    }
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

    //récupère la liste des amis qui ont joué au jeu
    public List<String> obtenirAmisFb(AccessToken accessToken) {
        final List<String> listeAmis = new ArrayList<String>();
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {

                Log.e("Liste amis: 1", response.toString());
                try {
                    JSONObject responseObject = response.getJSONObject();
                    JSONArray dataArray = responseObject.getJSONArray("data"); //recupere l'id et le nom des amis dans data

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String fbId = dataObject.getString("id");
                        String fbNom = dataObject.getString("name");
                        Log.e("FbId", fbId);
                        Log.e("FbNom", fbNom);
                        listeAmis.add(fbId);
                    }
                    Log.e("fbListeAmis", listeAmis.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).executeAsync();
        return listeAmis;
    }

    public boolean estUnAmiFB(String nom, List<String> amis) {
        boolean verif = false;
        for(String s : amis) {
            if(s.contains(nom))
                verif = true;
        }
        return verif;
    }

}
