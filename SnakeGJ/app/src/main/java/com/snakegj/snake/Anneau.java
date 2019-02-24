package com.snakegj.snake;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.snakegj.plan.Direction;

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

    public void avancer(int pasX, int pasY) {
        this.x += pasX;
        this.y += pasY;
        if(suivant != null) {
            x = suivant.x;
            y = suivant.y;
            suivant.avancer(pasX, pasY);
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
            suivant = new Anneau(x, y);
        }
    }

    public void dessiner(Canvas canvas, Bitmap image) {
        canvas.drawBitmap(image, x, y, null);
        if(suivant != null)
            suivant.dessiner(canvas, image);
    }

}
