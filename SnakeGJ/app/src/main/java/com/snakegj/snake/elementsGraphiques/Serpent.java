package com.snakegj.snake.elementsGraphiques;

import android.graphics.Canvas;

import com.snakegj.plan.Direction;
import com.snakegj.snake.Anneau;
import com.snakegj.snake.JeuVue;
import com.snakegj.snake.elementsGraphiques.ElementGraphique;

public class Serpent extends ElementGraphique {
    private Anneau tete;
    private Direction cap;
    private static int hauteurAnneau, largeurAnneau;
    private static final int PAS = 50; //déplacement du snake

    public Serpent() {
        tete = new Anneau(100, 200);
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
        tete.ajouterAnneau();
    }

    public void deplacer() {

        switch (cap) {
            case EST:
                tete.avancer(PAS, 0);
                if(estAuBord())
                    tete.setX(0);
                break;
            case OUEST:
                tete.avancer(-PAS, 0);
                if(estAuBord())
                    tete.setX(JeuVue.getLargeurEcran() - largeurAnneau);
                break;
            case SUD:
                tete.avancer(0, PAS);
                if(estAuBord())
                    tete.setY(0);
                break;
            case NORD:
                tete.avancer(0, -PAS);
                if(estAuBord())
                    tete.setY(JeuVue.getHauteurEcran() - hauteurAnneau);
                break;
        }
    }

    public boolean estAuBord() {
        return tete.getX() > JeuVue.getLargeurEcran() || tete.getY() > JeuVue.getHauteurEcran() || tete.getX() + largeurAnneau < 0 || tete.getY() + hauteurAnneau < 0;
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
