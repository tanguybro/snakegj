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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Menu extends AppCompatActivity {

    private LoginButton btnFb;
    private FirebaseAuth authen;
    private CallbackManager cm;

    private Button btnJouer;
    private Button btnClassement;
    private Button btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.menu);
        FirebaseApp.initializeApp(this);

        btnJouer = findViewById(R.id.jouer);
        btnClassement = findViewById(R.id.classement);
        btnOptions = findViewById(R.id.options);
        btnFb = findViewById(R.id.btnFacebookCo);

        authen = FirebaseAuth.getInstance();
        cm = CallbackManager.Factory.create();

        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Jeu.class));
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

        btnFb.setReadPermissions("email");
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seConnecter();
            }
        });

        obtenirCleHash();

    }

    private void seConnecter() {
        btnFb.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                infoIndent(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

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
}
