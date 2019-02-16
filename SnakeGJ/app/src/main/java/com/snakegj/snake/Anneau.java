package com.snakegj.snake;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.snakegj.plan.Direction;

public class Anneau {
    private Anneau suivant;
    private int x, y;

    public Anneau(Anneau a, int x, int y) {
        suivant = a;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void avancer(int pasX, int pasY) {
        this.x += pasX;
        this.y += pasY;
        if(suivant != null) {
            suivant.avancer(pasX, pasY);
        }
    }

    public void setX(int x, Direction cap) {
        this.x = x;
        if(suivant != null) {
            if(cap == Direction.EST)
                suivant.setX(x + Serpent.getLargeurAnneau(), cap);
            else
                suivant.setX(x - Serpent.getLargeurAnneau(), cap);
        }
    }

    public void setY(int y, Direction cap) {
        this.y = y;
        if(suivant != null) {
            if(cap == Direction.SUD)
                suivant.setY(y + Serpent.getHauteurAnneau(), cap);
            else
                suivant.setY(y - Serpent.getHauteurAnneau(), cap);
        }

    }

    public void ajouterAnneau(Direction cap) {
        if(suivant != null)
            suivant.ajouterAnneau(cap);
        else {
            switch (cap) {
                case OUEST:
                    suivant = new Anneau(null, x + Serpent.getLargeurAnneau(), y);
                    break;
                case EST:
                    suivant = new Anneau(null, x - Serpent.getLargeurAnneau(), y);
                    break;
                case NORD:
                    suivant = new Anneau(null, x, y - Serpent.getHauteurAnneau());
                    break;
                case SUD:
                    suivant = new Anneau(null, x, y + Serpent.getHauteurAnneau());
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
