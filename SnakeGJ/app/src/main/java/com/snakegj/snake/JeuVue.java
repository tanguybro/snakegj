package com.snakegj.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.snakegj.plan.Direction;

public class JeuVue extends SurfaceView implements SurfaceHolder.Callback {
    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private JeuThread jeuThread;
    private Serpent serpent;
    private Objet fruit;
    private Direction cap;

    // création de la surface de dessin
    public JeuVue(Context context) {
        super(context);
        getHolder().addCallback(this);
        jeuThread = new JeuThread(this);
        fruit = new Objet(this.getContext());
        serpent = new Serpent(this.getContext());
        cap = Direction.EST;
    }

    //dessine un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null) {return;}

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);

        // on dessine le snake
        serpent.dessiner(canvas);
        fruit.dessiner(canvas);
    }


    //gestion du déplacement du snake
    public void update() {

        serpent.deplacer(cap);

        if(serpent.getX() >= fruit.getX() &&
                serpent.getX() <= fruit.getX()+fruit.getLargeur() &&
                serpent.getY() >= fruit.getY() && serpent.getY() <= fruit.getY()+fruit.getHauteur() ) {
            serpent.manger();
            fruit.apparaitre();
        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus JeuThread si cela n'est pas fait
        if(jeuThread.getState()==Thread.State.TERMINATED) {
            jeuThread = new JeuThread(this);
        }
        jeuThread.setRunning(true);
        jeuThread.start();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de jeuThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        jeuThread.setRunning(false);
        while (retry) {
            try {
                jeuThread.join();
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

                if(currentX >= serpent.getX() && (cap == Direction.NORD || cap == Direction.SUD))
                    cap = Direction.EST;

                else if(currentX <= serpent.getX()+serpent.getLargeur() && (cap == Direction.NORD || cap == Direction.SUD))
                    cap = Direction.OUEST;

                else if(currentY >= serpent.getY() && (cap == Direction.OUEST || cap == Direction.EST))
                    cap = Direction.SUD;

                else if(currentY <= serpent.getY()+serpent.getHauteur() && (cap == Direction.EST || cap == Direction.OUEST))
                    cap = Direction.NORD;

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
        fruit.redimensionner(w, h);
    }
}
