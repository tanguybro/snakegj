package com.snakegj.jeu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.SurfaceHolder;

import com.snakegj.R;
import com.snakegj.jeu.snake.Anneau;
import com.snakegj.jeu.snake.elementsGraphiques.Serpent;
import com.snakegj.popup.PopupFinPartie;

public class ModeRevolution extends JeuVue {

    private BitmapDrawable tete;
    private static int score;
    private Context context;

    public ModeRevolution(Context context, Jeu jeu) {
        super(context, jeu);
        this.context = context;
        tete = (BitmapDrawable) ResourcesCompat.getDrawable(context.getResources(), R.drawable.gilet_jaune_tete, null);
        score = 0;
    }

    @Override
    public void doDraw(Canvas canvas) {
        if(canvas == null)
            return;
        canvas.drawColor(Color.WHITE);  // on efface l'écran, en blanc
        super.getFondJeu().dessiner(canvas);
        super.getFruit().dessiner(canvas);
        super.getSerpent().dessiner(canvas);
    }

    @Override
    public void update() {
        super.getSerpent().deplacer();
        if(super.getSerpent().seTouche())
            finPartie();
        if(estSurFruit()) {
            super.getSerpent().manger();
            super.getFruit().apparaitre(super.getSerpent());
            score++;
            super.getJeu().modifScore("SCORE : " + score);
        }
    }

    private void finPartie() {
        super.getJeuThread().setRunning(false);
        Intent intent = new Intent(context, PopupFinPartie.class);
        intent.putExtra("score", score);
        intent.putExtra("mode", "révolution");
        context.startActivity(intent);
        super.getJeu().finirActivite();
    }

    private boolean estSurFruit() {
        return !(super.getFruit().getX() >= super.getSerpent().getX() + Serpent.getLargeurAnneau()
                || super.getFruit().getX() + super.getFruit().getLargeur() <= super.getSerpent().getX()
                || super.getFruit().getY() >= super.getSerpent().getY() + Serpent.getHauteurAnneau()
                || super.getFruit().getY() + super.getFruit().getHauteur() <= super.getSerpent().getY());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.getFondJeu().modifierDimensions(w,h);
        super.getSerpent().redimensionner(getContext(), R.drawable.gilet_jaune, 12, 12);
        BitmapDrawable teteDim = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(tete.getBitmap(), w/12, h/12, true));
        Anneau.initImageTete(teteDim);
        super.getFruit().redimensionner(getContext(), R.drawable.police, 12, 15);
        super.getFondJeu().redimensionner(getContext(), R.drawable.background, 1, 1);
    }
}
