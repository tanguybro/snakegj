package com.snakegj.snake.elementsGraphiques;

import android.content.Context;
import android.graphics.Canvas;

public class FondJeu extends ElementGraphique {
    private int x, y;
    private int largeur, longueur;

    public FondJeu() {
        x = 0;
        y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void dessiner(Canvas canvas) {
        if(getImage() == null)
            return;
        canvas.drawBitmap(getImage().getBitmap(), x, y, null);
    }

    @Override
    public void modifierDimensions(int largeur, int hauteur) {
        this.largeur = largeur;
        this.longueur = longueur;
    }
}
