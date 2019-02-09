package com.snakegj.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.snakegj.R;

import java.util.ArrayList;
import java.util.Random;

public class Objet {
    private BitmapDrawable image;
    private int x, y;
    private int hauteur, largeur;
    private int hauteurEcran, largeurEcran;

    private Context contexte;

    public Objet(Context contexte) {
        this.contexte = contexte;
        x = 50;
        y = 250;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
        Drawable d = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true));
    }

    public void redimensionner(int largeurEcran, int hauteurEcran) {

        this.largeurEcran=largeurEcran;
        this.hauteurEcran=hauteurEcran;

        // on définit (au choix) la taille de la balle à 1/5ème de la largeur de l'écran
        largeur=largeurEcran/12;
        hauteur=hauteurEcran/15;
        image = setImage(contexte, R.drawable.ball,largeur,hauteur);
    }

    public void apparaitre() {
            int newX = (int) (Math.random() * (largeurEcran) - 25);
            int newY = (int) (Math.random() * (hauteurEcran) - 25);
            setX(newX);
            setY(newY);
            System.out.println(newX);
    }

    public boolean detecterCollision() {
        return x+largeur > largeurEcran || y+hauteur > hauteurEcran || x<0 || y<0;
    }

    public void dessiner(Canvas canvas) {
        if(image==null) {return;}
            canvas.drawBitmap(image.getBitmap(), x, y, null);
    }
}
