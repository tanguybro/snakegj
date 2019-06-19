package com.snakegj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import static com.google.ads.consent.ConsentStatus.NON_PERSONALIZED;
import static com.google.ads.consent.ConsentStatus.PERSONALIZED;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class Renseignement extends AppCompatActivity {

    private EditText champPseudo;
    private String TAG = this.getClass().getSimpleName();
    private ConsentForm form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renseignement);

        champPseudo = findViewById(R.id.chpPseudo);
        Button btnValider = findViewById(R.id.btnValiderPseudo);

        checkForConsent();

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(champPseudo.getText().toString().isEmpty())
                    Toast.makeText(Renseignement.this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show();
                else if(contientCaracSpeciaux(champPseudo.getText().toString()))
                    Toast.makeText(Renseignement.this, "Pseudo invalide : caractères spéciaux à retirer", Toast.LENGTH_SHORT).show();
                else {
                    CurrentUser.setPseudo(champPseudo.getText().toString());
                    startActivity(new Intent(Renseignement.this, Menu.class));
                    finish();
                }
            }
        });
    }

    private boolean contientCaracSpeciaux(String string) {
        return Pattern.compile("[@#$%*^¨&+-=()_<>.,;!?/]").matcher(string).find();
    }

    private void checkForConsent() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(Renseignement.this);
        String[] publisherIds = {"pub-id-from-publishers-like-admob"};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
                switch (consentStatus) {
                    case PERSONALIZED:
                        Log.d(TAG, "Showing Personalized ads");
                        showPersonalizedAds();
                        break;
                    case NON_PERSONALIZED:
                        Log.d(TAG, "Showing Non-Personalized ads");
                        showNonPersonalizedAds();
                        break;
                    case UNKNOWN:
                        Log.d(TAG, "Requesting Consent");
                        if (ConsentInformation.getInstance(getBaseContext())
                                .isRequestLocationInEeaOrUnknown()) {
                            requestConsent();
                        } else {
                            showPersonalizedAds();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });
    }

    private void requestConsent() {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL("https://snakegj.flycricket.io/privacy.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle error.
        }
        form = new ConsentForm.Builder(Renseignement.this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        // Consent form loaded successfully.
                        Log.d(TAG, "Requesting Consent: onConsentFormLoaded");
                        showForm();
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                        Log.d(TAG, "Requesting Consent: onConsentFormOpened");
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        Log.d(TAG, "Requesting Consent: onConsentFormClosed");
                        if (userPrefersAdFree) {
                            // Buy or Subscribe
                            Log.d(TAG, "Requesting Consent: User prefers AdFree");
                        } else {
                            Log.d(TAG, "Requesting Consent: Requesting consent again");
                            switch (consentStatus) {
                                case PERSONALIZED:
                                    showPersonalizedAds();break;
                                case NON_PERSONALIZED:
                                    showNonPersonalizedAds();break;
                                case UNKNOWN:
                                    showNonPersonalizedAds();break;
                            }

                        }
                        // Consent form was closed.
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        Log.d(TAG, "Requesting Consent: onConsentFormError. Error - " + errorDescription);
                        // Consent form error.
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .withAdFreeOption()
                .build();
        form.load();
    }

    private void showPersonalizedAds() {
        /*AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);*/
    }

    private void showNonPersonalizedAds() {
        /*AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, getNonPersonalizedAdsBundle())
                .build();
        mAdView.loadAd(adRequest);*/
    }

    private void showForm() {
        if (form == null) {
            Log.d(TAG, "Consent form is null");
        }
        if (form != null) {
            Log.d(TAG, "Showing consent form");
            form.show();
        } else {
            Log.d(TAG, "Not Showing consent form");
        }
    }


}
