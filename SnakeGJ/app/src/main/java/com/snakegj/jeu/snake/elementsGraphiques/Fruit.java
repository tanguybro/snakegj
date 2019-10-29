package com.snakegj.jeu.snake.elementsGraphiques;

import android.graphics.Canvas;
import android.util.Log;

public class Fruit extends ElementGraphique {
    private int x, y;
    private int hauteur, largeur;

    public Fruit() {
        x = 270;
        y = 345;
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

    public void apparaitre(Serpent serpent) {
        do {
            int uniteX = FondJeu.getLargeur() / Serpent.getLargeurAnneau();
            int uniteY = FondJeu.getHauteur() / Serpent.getHauteurAnneau();
            int randomX = randomNumber(0, uniteX);
            int randomY = randomNumber(0, uniteY);
            int xtemp = randomX * Serpent.getLargeurAnneau();
            int ytemp = randomY * Serpent.getHauteurAnneau();

            if(xtemp > FondJeu.getLargeur() - Serpent.getLargeurAnneau())
                x = FondJeu.getLargeur() - Serpent.getLargeurAnneau();
            else
                x = xtemp;

            if(ytemp > FondJeu.getHauteur() - Serpent.getHauteurAnneau())
                y = FondJeu.getHauteur() - Serpent.getHauteurAnneau();
            else
                y = ytemp;

        }
        while(serpent.posDansSerpent(x,y));
    }

    private int randomNumber(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
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
