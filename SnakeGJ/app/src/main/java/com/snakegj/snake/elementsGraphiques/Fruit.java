package com.snakegj.snake.elementsGraphiques;

import android.graphics.Canvas;

import com.snakegj.snake.JeuVue;
import com.snakegj.snake.elementsGraphiques.ElementGraphique;

public class Fruit extends ElementGraphique {
    private int x, y;
    private int hauteur, largeur;

    public Fruit() {
        x = 0;
        y = 0;
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

    public void apparaitre() {
        x = (int) (Math.random() * (JeuVue.getLargeurEcran()) - 25);
        y = (int) (Math.random() * (JeuVue.getHauteurEcran()) - 25);
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
