package com.snakegj.snake;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.snakegj.plan.Direction;
import com.snakegj.snake.elementsGraphiques.Serpent;

public class Anneau {
    private Anneau suivant;
    private int x, y;

    public Anneau(int x, int y) {
        this(null, x, y);
    }

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

    public Anneau getSuivant() {
        return suivant;
    }

    public void avancer(int posX, int posY, boolean estTete) {
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
            suivant.avancer(tempX, tempY, false);
        }
        if(estTete) {
            this.x = posX;
            this.y = posY;
        }
    }

    public void setX(int x) {
        this.x = x;
        if(suivant != null) {
            suivant.setX(x);
        }
    }

    public void setY(int y) {
        this.y = y;
        if(suivant != null) {
            suivant.setY(y);
        }
    }

    public void ajouterAnneau(Direction cap) {
        if(suivant != null)
            suivant.ajouterAnneau(cap);
        else {
            switch (cap) {
                case EST:
                    suivant = new Anneau(x - 2*Serpent.getLargeurAnneau(), y);
                    break;
                case OUEST:
                    suivant = new Anneau(x + 2*Serpent.getLargeurAnneau(), y);
                    break;
                case SUD:
                    suivant = new Anneau(x, y - 2*Serpent.getHauteurAnneau());
                    break;
                case NORD:
                    suivant = new Anneau(x, y + 2*Serpent.getHauteurAnneau());
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
