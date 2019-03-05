package com.snakegj.snake.elementsGraphiques;

import android.graphics.Canvas;

public class FondJeu extends ElementGraphique {
    private static int largeur, hauteur;

    public static int getHauteur() {
        return hauteur;
    }

    public static int getLargeur() {
        return largeur;
    }

    public void dessiner(Canvas canvas) {
        if(getImage() == null)
            return;
        canvas.drawBitmap(getImage().getBitmap(), 0, 0, null);
    }

    @Override
    public void modifierDimensions(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }
}
