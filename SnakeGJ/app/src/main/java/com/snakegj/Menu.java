package com.snakegj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
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
import com.snakegj.jeu.Jeu;
import com.snakegj.pages.Classement;
import com.snakegj.pages.Options;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Menu extends AppCompatActivity {
    private LoginButton btnFb;
    private FirebaseAuth authen;
    private CallbackManager cm;

    private Button btnJouer;
    private Button btnClassement;
    private Button btnOptions;

    private EditText chpPseudo;
    private FrameLayout interfacePseudo;

    private static MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = MediaPlayer.create(this, R.raw.open);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(preferences.getString("etat musique", "Off").equals("On")) {
            player.setLooping(true);
            player.start();
        }
        else {
            player.stop();
        }


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
                if(contientCaracSpeciaux(chpPseudo.getText().toString()) && !estConnecteFB())
                    Toast.makeText(Menu.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();
                else if(!chpPseudo.getText().toString().isEmpty() && !estConnecteFB()) {
                    Intent intent = new Intent(Menu.this, Jeu.class);
                    intent.putExtra("pseudo", chpPseudo.getText().toString());
                    player.stop();
                    startActivity(intent);
                }
                else if(chpPseudo.getText().toString().isEmpty() && !estConnecteFB())
                    Toast.makeText(Menu.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(Menu.this, Jeu.class);
                    intent.putExtra("pseudo", Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
                    player.stop();
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

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seConnecter();
            }
        });

        if(AccessToken.getCurrentAccessToken() != null)
            cacherInterface();

        obtenirCleHash(); //pour lier app et login facebook
    }

    static boolean contientCaracSpeciaux(String string) {
        return Pattern.compile("[@#$%*^¨&+-=()_<>.,;!?/]").matcher(string).find();
    }

    private void seConnecter() {
        btnFb.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                infoIndent(loginResult.getAccessToken());
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

    public static boolean estConnecteFB() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void infoIndent(AccessToken accessToken) {
        //recupere le type d'authentification (fb, google, twitter...)
        AuthCredential c = FacebookAuthProvider.getCredential(accessToken.getToken());
        authen.signInWithCredential(c).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Menu.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Menu.this, "Vous êtes connecté", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Lorsqu'il y a une action avec le login
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

    public static MediaPlayer getPlayer() {
        return player;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
