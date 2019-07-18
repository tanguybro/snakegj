package com.snakegj.jeu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.snakegj.jeu.plan.Direction;
import com.snakegj.jeu.snake.elementsGraphiques.FondJeu;
import com.snakegj.jeu.snake.elementsGraphiques.Fruit;
import com.snakegj.jeu.snake.elementsGraphiques.Serpent;

public abstract class JeuVue extends SurfaceView implements SurfaceHolder.Callback {

    private JeuThread jeuThread;
    private Jeu jeu;
    private Serpent serpent;
    private Fruit fruit;
    private FondJeu fondJeu;

    public JeuVue(Context context, Jeu jeu) {
        super(context);
        getHolder().addCallback(this);
        jeuThread = new JeuThread(this);
        serpent = new Serpent();
        fruit = new Fruit();
        fondJeu = new FondJeu();
        this.jeu = jeu;
    }

    public abstract void doDraw(Canvas canvas);
    public abstract void update();

    public JeuThread getJeuThread() {
        return jeuThread;
    }

    public Serpent getSerpent() {
        return serpent;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public FondJeu getFondJeu() {
        return fondJeu;
    }

    public Jeu getJeu() {
        return jeu;
    }

    // Gère les touchés sur l'écran
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        Direction cap = serpent.getCap();

        if(event.getAction() == MotionEvent.ACTION_DOWN) { // code exécuté lorsque le doigt touche l'écran.
            if (x >= serpent.getX() && (cap == Direction.NORD || cap == Direction.SUD))
                serpent.setCap(Direction.EST);
            else if (x <= serpent.getX() && (cap == Direction.NORD || cap == Direction.SUD))
                serpent.setCap(Direction.OUEST);
            else if (y >= serpent.getY() && (cap == Direction.OUEST || cap == Direction.EST))
                serpent.setCap(Direction.SUD);
            else if (y <= serpent.getY() && (cap == Direction.EST || cap == Direction.OUEST))
                serpent.setCap(Direction.NORD);
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
}
