package com.snakegj;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Menu extends AppCompatActivity {

    private LoginButton btnFb;
    private FirebaseAuth authen;
    private CallbackManager cm;

    private Button btnJouer;
    private Button btnClassement;
    private Button btnOptions;

    private EditText chpPseudo;
    private FrameLayout interfacePseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //A corriger
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.menu);
        FirebaseApp.initializeApp(this);

        btnJouer = findViewById(R.id.jouer);
        btnClassement = findViewById(R.id.classement);
        btnOptions = findViewById(R.id.options);
        btnFb = findViewById(R.id.btnFacebookCo);
        chpPseudo = findViewById(R.id.chpPseudo);
        interfacePseudo = findViewById(R.id.interfacePseudo);

        authen = FirebaseAuth.getInstance();
        cm = CallbackManager.Factory.create();

        //Le pseudo ne doit pas contenir de caracteres speciaux (a faire)
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chpPseudo.getText().toString().isEmpty() && !estConnecte()) {
                    Intent intent = new Intent(Menu.this, Jeu.class);
                    intent.putExtra("pseudo", chpPseudo.getText().toString());
                    startActivity(intent);
                }
                else if(chpPseudo.getText().toString().isEmpty() && !estConnecte())
                    Toast.makeText(Menu.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(Menu.this, Jeu.class);
                    intent.putExtra("pseudoFB", Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
                    startActivity(intent);
                }
            }
        });

        btnClassement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Classement.class));
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Options.class));
            }
        });

        btnFb.setReadPermissions(Arrays.asList("email", "user_friends"));
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seConnecter();
            }
        });

        if(AccessToken.getCurrentAccessToken() != null)
            cacherInterface();

        obtenirCleHash(); //pour app login facebook

    }

    private void seConnecter() {
        btnFb.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                infoIndent(loginResult.getAccessToken());
                obtenirAmisFb(loginResult.getAccessToken());
                cacherInterface();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public static boolean estConnecte() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void infoIndent(AccessToken accessToken) {
        AuthCredential c = FacebookAuthProvider.getCredential(accessToken.getToken());
        authen.signInWithCredential(c).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Menu.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String email = authResult.getUser().getEmail();
                Toast.makeText(Menu.this, "Vous êtes connecté", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenirAmisFb(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            final JSONObject object,
                            GraphResponse response) {
                        // Application code
                        final JSONObject jsonObject = response.getJSONObject();
                        String first_name = "";
                        String last_name = "";
                        try {
                            first_name = jsonObject.getString("first_name");
                            last_name =  jsonObject.getString("last_name");
                            JSONObject friends = jsonObject.getJSONObject("friends");
                            JSONArray data = friends.getJSONArray("data");
                            JSONObject objectdata = data.getJSONObject(0);
                            String friend_first_name = objectdata.getString("first_name");
                            String friend_last_name = objectdata.getString("last_name");
                            Log.d("Prenom", friend_first_name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }}});

        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(
                AccessToken oldAccessToken,
                AccessToken currentAccessToken) {

            if (currentAccessToken == null){
                apparaitreInterface();
            }
        }
    };

    public void obtenirCleHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.snakegj", PackageManager.GET_SIGNATURES);
            for(Signature s:info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(s.toByteArray());
                Log.e("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public void cacherInterface() {
        interfacePseudo.setVisibility(View.GONE);
    }

    public void apparaitreInterface() {
        interfacePseudo.setVisibility(View.VISIBLE);
    }
}
