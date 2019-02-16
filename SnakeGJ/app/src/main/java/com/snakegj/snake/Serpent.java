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
    private Direction cap;

    //déplacement du snake
    private static final int PAS = 5;

    public Serpent() {
        anneaux = new ArrayList<BitmapDrawable>();
        cap = Direction.EST;
        x = 100;
        y = 250;
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

    public Direction getCap() {
        return cap;
    }

    public void setCap(Direction cap) {
        this.cap = cap;
    }

    public BitmapDrawable setImage(Context c, int ressource, int largeur, int hauteur) {
        Drawable d = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true));
    }

    public void redimensionner(Context c) {
        largeur = JeuVue.getLargeurEcran() / 10;
        hauteur = JeuVue.getHauteurEcran() / 10;
        image = setImage(c, R.drawable.gilet_jaune,largeur,hauteur);
        anneaux.add(image);
    }

    public void manger() {
        anneaux.add(image);
    }

    public boolean estAuBord() {
        return x + largeur >= JeuVue.getLargeurEcran() || y + hauteur >= JeuVue.getHauteurEcran() || x <= 0 || y <= 0;
    }

    public void deplacer() {

        //remplacer l'avant-dernier anneau par le dernier etc...

        switch (cap) {
            case EST:
                allerADroite();
                if(estAuBord())
                    x = 1;
                break;
            case OUEST:
                allerAGauche();
                if(estAuBord())
                    x = JeuVue.getLargeurEcran() - largeur - 1;
                break;
            case NORD:
                allerEnHaut();
                if(estAuBord())
                    y = JeuVue.getHauteurEcran() - hauteur - 1;
                break;
            case SUD:
                allerEnBas();
                if(estAuBord())
                    y = 1;
                break;
        }
    }

    public void allerEnHaut() {
        cap = Direction.NORD;
        y -= PAS;
    }

    public void allerEnBas() {
        cap = Direction.SUD;
        y += PAS;
    }

    public void allerAGauche() {
        cap = Direction.OUEST;
        x -= PAS;
    }

    public void allerADroite() {
        cap = Direction.EST;
        x += PAS;
    }

    public void dessiner(Canvas canvas) {
        int decalage = 0;
        if(image == null)
            return;
        for(BitmapDrawable b : anneaux) {
            if(cap == Direction.EST) {
                canvas.drawBitmap(b.getBitmap(), x + decalage, y, null);
                decalage -= getLargeur();
            }
            if(cap == Direction.OUEST) {
                canvas.drawBitmap(b.getBitmap(), x + decalage, y, null);
                decalage += getLargeur();
            }
            if(cap == Direction.NORD) {
                canvas.drawBitmap(b.getBitmap(), x, y + decalage, null);
                decalage += getHauteur();
            }
            if(cap == Direction.SUD) {
                canvas.drawBitmap(b.getBitmap(), x, y + decalage, null);
                decalage -= getHauteur();
            }
        }
    }


}
