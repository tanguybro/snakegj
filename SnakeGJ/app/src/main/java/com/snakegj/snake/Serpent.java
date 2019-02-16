package com.snakegj.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.snakegj.R;
import com.snakegj.plan.Direction;

import java.util.ArrayList;

public class Serpent extends Element {
    private Anneau tete;
    private Direction cap;
    private static int hauteurAnneau, largeurAnneau;
    private static final int PAS = 5; //déplacement du snake

    public Serpent() {
        tete = new Anneau(null, 100, 200);
        cap = Direction.EST;
    }

    public static int getHauteurAnneau() {
        return hauteurAnneau;
    }

    public static int getLargeurAnneau() {
        return largeurAnneau;
    }

    public int getX() {
        return tete.getX();
    }

    public int getY() {
        return tete.getY();
    }

    public Direction getCap() {
        return cap;
    }

    public void setCap(Direction cap) {
        this.cap = cap;
    }

    public void manger() {
        tete.ajouterAnneau(cap);
    }

    public void deplacer() {
        /**remplacer l'avant-dernier anneau par le dernier etc...*/

        switch (cap) {
            case EST:
                tete.avancer(PAS, 0);
                if(estAuBord())
                    tete.setX(0, cap);
                break;
            case OUEST:
                tete.avancer(-PAS, 0);
                if(estAuBord())
                    tete.setX(JeuVue.getLargeurEcran() - largeurAnneau, cap);
                break;
            case SUD:
                tete.avancer(0, PAS);
                if(estAuBord())
                    tete.setY(0, cap);
                break;
            case NORD:
                tete.avancer(0, -PAS);
                if(estAuBord())
                    tete.setY(JeuVue.getHauteurEcran() - hauteurAnneau, cap);
                break;
        }
    }

    public boolean estAuBord() {
        return tete.getX() + largeurAnneau > JeuVue.getLargeurEcran() || tete.getY() + hauteurAnneau > JeuVue.getHauteurEcran() || tete.getX() < 0 || tete.getY() < 0;
    }

    public void dessiner(Canvas canvas) {
        if(getImage() == null)
            return;
        tete.dessiner(canvas, getImage().getBitmap());
    }

    @Override
    public void modifierDimensions(int largeur, int hauteur) {
        largeurAnneau = largeur;
        hauteurAnneau = hauteur;
    }

}
