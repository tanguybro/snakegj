package com.snakegj.snake.elementsGraphiques;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;

import com.snakegj.snake.JeuVue;

public abstract class ElementGraphique {
    private BitmapDrawable image;

    public abstract void modifierDimensions(int largeur, int hauteur);

    protected BitmapDrawable getImage() {
        return image;
    }

    private void setImage(Context c, int ressource, int largeur, int hauteur) {
        BitmapDrawable b = (BitmapDrawable) ResourcesCompat.getDrawable(c.getResources(), ressource, null);
        image = new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(b.getBitmap(), largeur, hauteur, true));
    }

    public void redimensionner(Context c, int ressource, int proportionLargeur, int proportionHauteur) {
        modifierDimensions(JeuVue.getLargeurEcran() / proportionLargeur, JeuVue.getHauteurEcran() / proportionHauteur);
        setImage(c, ressource, JeuVue.getLargeurEcran() / proportionLargeur, JeuVue.getHauteurEcran() / proportionHauteur);
    }
}
