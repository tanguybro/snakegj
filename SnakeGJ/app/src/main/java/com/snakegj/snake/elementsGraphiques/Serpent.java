package com.snakegj.snake.elementsGraphiques;

import android.graphics.Canvas;

import com.snakegj.plan.Direction;
import com.snakegj.snake.Anneau;

public class Serpent extends ElementGraphique {
    private Anneau tete;
    private Direction cap;
    private static int hauteurAnneau, largeurAnneau;
    private static final int PAS = 85; //déplacement du snake

    public Serpent() {
        tete = new Anneau(100, 200); /** Debut aleatoire ? */
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
        switch (cap) {
            case EST:
                tete.avancerA(getX() + PAS, getY(), true);
                if(estAuBord())
                    tete.avancerA(0, getY(), true);
                break;
            case OUEST:
                tete.avancerA(getX() - PAS, getY(), true);
                if(estAuBord())
                    tete.avancerA(FondJeu.getLargeur() - largeurAnneau , getY(), true);
                break;
            case SUD:
                tete.avancerA(getX(), getY() + PAS, true);
                if(estAuBord())
                    tete.avancerA(getX(), 0, true);
                break;
            case NORD:
                tete.avancerA(getX(), getY() - PAS ,true);
                if(estAuBord())
                    tete.avancerA(getX(), FondJeu.getHauteur() - hauteurAnneau, true);
                break;
        }
    }

    public boolean estAuBord() {
        return tete.getX() > FondJeu.getLargeur() || tete.getY() > FondJeu.getHauteur() || tete.getX() + largeurAnneau < 0 || tete.getY() + hauteurAnneau < 0;
    }

    public boolean seTouche() {
        Anneau a = tete.getSuivant();
        if(a == null)
            return false;
        while(a.getSuivant() != null) {
            if(teteTouche(a))
                return true;
            a = a.getSuivant();
        }
        return false;
    }

    private boolean teteTouche(Anneau a) {
        return (tete.getX() == a.getX() && tete.getY() == a.getY());
    }

    public boolean posDansSerpent(int x, int y) {
        return tete.posDansSerpent(x,y);
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
