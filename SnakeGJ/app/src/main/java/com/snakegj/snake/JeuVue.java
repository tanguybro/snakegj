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
    private Fruit fruit;
    private static int hauteurEcran, largeurEcran;

    // création de la surface de dessin
    public JeuVue(Context context) {
        super(context);
        getHolder().addCallback(this);
        jeuThread = new JeuThread(this);
        fruit = new Fruit();
        serpent = new Serpent();
    }

    public static int getHauteurEcran() {
        return hauteurEcran;
    }

    public static int getLargeurEcran() {
        return largeurEcran;
    }

    //dessine un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas==null) {
            return;
        }
        canvas.drawColor(Color.WHITE);  // on efface l'écran, en blanc
        serpent.dessiner(canvas);
        fruit.dessiner(canvas);
    }

    //gestion du serpent
    public void update() {
        serpent.deplacer();

        if(!detecteCollision()) {
              serpent.manger();
              fruit.apparaitre();
        }
    }

    public boolean detecteCollision() {
        return fruit.getX() >= serpent.getX() + serpent.getLargeur()
                || fruit.getX() + fruit.getLargeur() <= serpent.getX()
                || fruit.getY() >= serpent.getY() + serpent.getHauteur()
                || fruit.getY() + fruit.getHauteur() <= serpent.getY();
    }

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();
        Direction cap = serpent.getCap();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:

                if(currentX >= serpent.getX() && (cap == Direction.NORD || cap == Direction.SUD))
                    serpent.setCap(Direction.EST);

                else if(currentX <= serpent.getX()+serpent.getLargeur() && (cap == Direction.NORD || cap == Direction.SUD))
                    serpent.setCap(Direction.OUEST);

                else if(currentY >= serpent.getY() && (cap == Direction.OUEST || cap == Direction.EST))
                    serpent.setCap(Direction.SUD);

                else if(currentY <= serpent.getY()+serpent.getHauteur() && (cap == Direction.EST || cap == Direction.OUEST))
                    serpent.setCap(Direction.NORD);

                break;
        }
        return true;  // On retourne vrai pour indiquer qu'on a géré l'évènement
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

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        hauteurEcran = h;
        largeurEcran = w;
        serpent.redimensionner(getContext());
        fruit.redimensionner(getContext());
    }
}
