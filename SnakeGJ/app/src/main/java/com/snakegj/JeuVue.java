package com.snakegj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.snakegj.plan.Direction;

public class JeuVue extends SurfaceView implements SurfaceHolder.Callback {
    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private SerpentThread serpentThread;
    private Serpent serpent;
    private Direction cap;

    // création de la surface de dessin
    public JeuVue(Context context) {
        super(context);
        getHolder().addCallback(this);
        serpentThread = new SerpentThread(this);

        serpent = new Serpent(this.getContext());
    }

    //dessine un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        // on dessine le snake
        serpent.dessiner(canvas);
    }


    //gestion du déplacement du snake
    public void update() {
        if(cap == Direction.SUD) {
            serpent.allerEnBas();
        }
        if(cap == Direction.NORD) {
            serpent.allerEnHaut();
        }
        if(cap == Direction.EST) {
            serpent.allerADroite();
        }
        if(cap == Direction.OUEST) {
            serpent.allerAGauche();
        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus SerpentThread si cela n'est pas fait
        if(serpentThread.getState()==Thread.State.TERMINATED) {
            serpentThread = new SerpentThread(this);
        }
        serpentThread.setRunning(true);
        serpentThread.start();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de serpentThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        serpentThread.setRunning(false);
        while (retry) {
            try {
                serpentThread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:

                if(currentX >= serpent.getX()) {
                    cap = Direction.EST;
                }

                if(currentX <= serpent.getX()+serpent.getLargeur()) {
                    cap = Direction.OUEST;
                }

                if(currentY >= serpent.getY()) {
                    cap = Direction.SUD;
                }

                if(currentY <= serpent.getY()+serpent.getHauteur()) {
                    cap = Direction.NORD;
                }

                break;



        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        serpent.redimensionner(w,h); // on définit la taille de la balle selon la taille de l'écran
    }
}
