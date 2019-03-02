package com.snakegj.snake.elementsGraphiques;

import android.graphics.Canvas;
import android.util.Log;

import com.snakegj.plan.Direction;
import com.snakegj.snake.Anneau;
import com.snakegj.snake.JeuVue;
import com.snakegj.snake.elementsGraphiques.ElementGraphique;

public class Serpent extends ElementGraphique {
    private Anneau tete;
    private Direction cap;
    private boolean touchee; //Provisoire
    private static int hauteurAnneau, largeurAnneau;
    private static final int PAS = 85; //déplacement du snake

    public Serpent() {
        tete = new Anneau(100, 200);
        cap = Direction.EST;
        touchee = false;
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

        switch (cap) {
            case EST:
                tete.avancer(getX() + PAS, getY(), true);
                if(estAuBord())
                    tete.avancer(0, getY(), true);
                break;
            case OUEST:
                tete.avancer(getX() - PAS, getY(), true);
                if(estAuBord())
                    tete.avancer(JeuVue.getLargeurEcran() - largeurAnneau , getY(), true);
                break;
            case SUD:
                tete.avancer(getX(), getY() + PAS, true);
                if(estAuBord())
                    tete.avancer(getX(), 0, true);
                break;
            case NORD:
                tete.avancer(getX(), getY() - PAS ,true);
                if(estAuBord())
                    tete.avancer(getX(), JeuVue.getHauteurEcran() - hauteurAnneau, true);
                break;
        }
    }

    public boolean estAuBord() {
        return tete.getX() > JeuVue.getLargeurEcran() || tete.getY() > JeuVue.getHauteurEcran() || tete.getX() + largeurAnneau < 0 || tete.getY() + hauteurAnneau < 0;
    }

    public boolean seTouche() {
        parcoursSerpent(tete.getSuivant());
        Log.d("Mort", "" + touchee);
        return touchee;
    }

    private void parcoursSerpent(Anneau a) {
        if(a == null)
            return;
        if(!anneauTouche(a))
            touchee = true;
        parcoursSerpent(a.getSuivant());
    }

    //nom a changer
    private boolean anneauTouche(Anneau a) {
            return (a.getX() >= tete.getX() + largeurAnneau)
                    || (a.getX() + largeurAnneau <= tete.getX())
                    || (a.getY() >= tete.getY() + hauteurAnneau)
                    || (a.getY() + hauteurAnneau <= tete.getY());
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
