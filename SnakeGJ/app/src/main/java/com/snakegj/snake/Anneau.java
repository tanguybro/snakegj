package com.snakegj.snake;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.snakegj.plan.Direction;
import com.snakegj.snake.elementsGraphiques.Serpent;

public class Anneau {
    private Anneau suivant;
    private int x, y;

    public Anneau(int x, int y) {
        suivant = null;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Anneau getSuivant() {
        return suivant;
    }

    public void avancerA(int posX, int posY, boolean estTete) {
        if(suivant != null) {
            int tempX = suivant.x;
            int tempY = suivant.y;
            if(estTete) {
                suivant.x = this.x;
                suivant.y = this.y;
            }
            else {
                suivant.x = posX;
                suivant.y = posY;
            }
            suivant.avancerA(tempX, tempY, false);
        }
        if(estTete) {
            this.x = posX;
            this.y = posY;
        }
    }

    public void ajouterAnneau(Direction cap) {
        if(suivant != null)
            suivant.ajouterAnneau(cap);
        else {
            switch (cap) {
                case EST:
                    suivant = new Anneau(x - Serpent.getLargeurAnneau(), y);
                    break;
                case OUEST:
                    suivant = new Anneau(x + Serpent.getLargeurAnneau(), y);
                    break;
                case SUD:
                    suivant = new Anneau(x, y - Serpent.getHauteurAnneau());
                    break;
                case NORD:
                    suivant = new Anneau(x, y + Serpent.getHauteurAnneau());
                    break;
            }
        }
    }

    public void dessiner(Canvas canvas, Bitmap image) {
        canvas.drawBitmap(image, x, y, null);
        if(suivant != null)
            suivant.dessiner(canvas, image);
    }

}
