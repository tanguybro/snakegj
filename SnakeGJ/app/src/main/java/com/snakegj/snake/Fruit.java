package com.snakegj.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.snakegj.R;

import java.util.ArrayList;
import java.util.Random;

public class Fruit extends Element {
    private int x, y;
    private int hauteur, largeur;

    public Fruit() {
      //  apparaitre(); Met en (0,0) jsp pq
        x = 100;
        y = 100;
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

    public void apparaitre() {
        x = (int) (Math.random() * (JeuVue.getLargeurEcran()) - 25);
        y = (int) (Math.random() * (JeuVue.getHauteurEcran()) - 25);
    }

    public void dessiner(Canvas canvas) {
        if(getImage() == null)
            return;
        canvas.drawBitmap(getImage().getBitmap(), x, y, null);
    }

    @Override
    public void modifierDimensions(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

}
