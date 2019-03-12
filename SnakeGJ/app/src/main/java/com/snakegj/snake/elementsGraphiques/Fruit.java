package com.snakegj.snake.elementsGraphiques;

import android.graphics.Canvas;

public class Fruit extends ElementGraphique {
    private int x, y;
    private int hauteur, largeur;

    public Fruit() {
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

    public void apparaitre(Serpent serpent) {
        do {
            x = (int) (Math.random() * (FondJeu.getLargeur() - (largeur/2)));
            y = (int) (Math.random() * (FondJeu.getHauteur() - (hauteur/2)));
        }
        while(serpent.posDansSerpent(x,y));
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
