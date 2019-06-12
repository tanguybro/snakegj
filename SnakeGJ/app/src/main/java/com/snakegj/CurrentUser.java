package com.snakegj;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CurrentUser {

    private static SharedPreferences preferences;
    private static int meilleurScore;
    private static String pseudo;

    public static void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        meilleurScore = preferences.getInt("Meilleur Score", 0);
        CurrentUser.pseudo = preferences.getString("Pseudo", "");
    }

    public static String getPseudo() {
        return pseudo;
    }

    public static int getMeilleurScore() {
        return meilleurScore;
    }

    public static void setMeilleurScore(int score) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Meilleur Score", score);
        editor.apply();
        meilleurScore = score;
    }

}
