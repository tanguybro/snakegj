package com.snakegj.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.snakegj.R;
import com.snakegj.plan.Direction;

import java.util.ArrayList;

public class Serpent {
    private ArrayList<BitmapDrawable> anneaux;
    private BitmapDrawable image;
    private int x, y;
    private int hauteur, largeur;
    private int hauteurEcran, largeurEcran;
    private Direction cap;


    //déplacement du snake
    private static final int PAS = 5;
    private int vitesseX = PAS, vitesseY = PAS;
    private Context contexte;

    public Serpent(Context contexte) {
        this.contexte = contexte;
        anneaux = new ArrayList<BitmapDrawable>();
        anneaux.add(image);
        cap = Direction.EST;
        x = 100;
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

    public int getHauteurEcran() {
        return hauteurEcran;
    }

    public int getLargeurEcran() {
        return largeurEcran;
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
        largeur=largeurEcran/10;
        hauteur=hauteurEcran/10;
        image = setImage(contexte, R.drawable.gilet_jaune,largeur,hauteur);
    }

    public void manger() {
        anneaux.add(image);
    }

    public boolean detecterCollision()
    {
        return x+largeur > largeurEcran || y+hauteur > hauteurEcran || x<0 || y<0;
    }

    public void deplacer(Direction cap) {

        switch (cap) {
            case EST:
                allerADroite();
                break;
            case OUEST:
                allerAGauche();
                break;
            case NORD:
                allerEnHaut();
                break;
            case SUD:
                allerEnBas();
                break;

        }
    }

    public void allerEnHaut() {
        cap = Direction.NORD;
        y -= vitesseY;
    }

    public void allerEnBas() {
        cap = Direction.SUD;
        y += vitesseY;
    }

    public void allerAGauche() {
        cap = Direction.OUEST;
        x -= vitesseX;
    }

    public void allerADroite() {
        cap = Direction.EST;
        x += vitesseX;
    }

    public void dessiner(Canvas canvas)
    {
        int decalage = 0;
        if(image==null) {return;}
        for(BitmapDrawable b : anneaux) {
            if(cap == Direction.EST) {
                canvas.drawBitmap(image.getBitmap(), x + decalage, y, null);
                decalage -= getLargeur();
            }
            if(cap == Direction.OUEST) {
                canvas.drawBitmap(image.getBitmap(), x + decalage, y, null);
                decalage += getLargeur();
            }
            if(cap == Direction.NORD) {
                canvas.drawBitmap(image.getBitmap(), x, y + decalage, null);
                decalage += getHauteur();
            }
            if(cap == Direction.SUD) {
                canvas.drawBitmap(image.getBitmap(), x, y + decalage, null);
                decalage -= getHauteur();
            }
        }
    }



}
