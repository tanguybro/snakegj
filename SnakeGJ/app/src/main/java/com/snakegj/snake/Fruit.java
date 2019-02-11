package com.snakegj.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.snakegj.R;

import java.util.ArrayList;
import java.util.Random;

public class Fruit {
    private BitmapDrawable image;
    private int x, y;
    private int hauteur, largeur;

    public Fruit() {
        apparaitre();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public BitmapDrawable setImage(Context c, int ressource, int largeur, int hauteur) {
        BitmapDrawable b = (BitmapDrawable) c.getResources().getDrawable(ressource);
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(b.getBitmap(), largeur, hauteur, true));
    }

    public void redimensionner(Context c) {
        largeur = JeuVue.getLargeurEcran() / 12;
        hauteur = JeuVue.getHauteurEcran() / 15;
        image = setImage(c, R.drawable.ball,largeur,hauteur);
    }

    public void apparaitre() {
        x = (int) (Math.random() * (JeuVue.getLargeurEcran()) - 25);
        y = (int) (Math.random() * (JeuVue.getHauteurEcran()) - 25);
    }

    public void dessiner(Canvas canvas) {
        if(image == null)
            return;
        canvas.drawBitmap(image.getBitmap(), x, y, null);
    }
}
