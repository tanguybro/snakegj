package com.snakegj.snake;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.snakegj.plan.Direction;

public class Anneau {
    private Anneau precedent;
    private int x, y;

    public Anneau(int x, int y) {
        this(null, x, y);
    }

    public Anneau(Anneau a, int x, int y) {
        precedent = a;
        placerA(x, y);
    }

    public void placerA(int x, int y) {
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
        placerA(precedent.x, precedent.y);
    }

    public void setX(int x, Direction cap) {
        this.x = x;
        if(precedent != null) {
            if(cap == Direction.EST)
                precedent.setX(x - Serpent.getLargeurAnneau(), cap);
            else
                precedent.setX(x + Serpent.getLargeurAnneau(), cap);
        }
    }

    public void setY(int y, Direction cap) {
        this.y = y;
        if(precedent != null) {
            if(cap == Direction.SUD)
                precedent.setY(y - Serpent.getHauteurAnneau(), cap);
            else
                precedent.setY(y + Serpent.getHauteurAnneau(), cap);
        }

    }

    public void ajouterAnneau(Direction cap) {
        if(precedent != null)
            precedent.ajouterAnneau(cap);
        else {
            switch (cap) {
                case OUEST:
                    precedent = new Anneau(null, x + Serpent.getLargeurAnneau(), y);
                    break;
                case EST:
                    precedent = new Anneau(null, x - Serpent.getLargeurAnneau(), y);
                    break;
                case NORD:
                    precedent = new Anneau(null, x, y + Serpent.getHauteurAnneau());
                    break;
                case SUD:
                    precedent = new Anneau(null, x, y - Serpent.getHauteurAnneau());
                    break;
            }
        }
    }

    public void dessiner(Canvas canvas, Bitmap image) {
        canvas.drawBitmap(image, x, y, null);
        if(precedent != null)
            precedent.dessiner(canvas, image);
    }

}
