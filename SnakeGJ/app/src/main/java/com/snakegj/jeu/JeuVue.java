package com.snakegj.jeu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.snakegj.jeu.plan.Direction;
import com.snakegj.jeu.snake.elementsGraphiques.FondJeu;
import com.snakegj.jeu.snake.elementsGraphiques.Fruit;
import com.snakegj.jeu.snake.elementsGraphiques.Serpent;

public abstract class JeuVue extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnGestureListener {

    private JeuThread jeuThread;
    private Jeu jeu;
    private Serpent serpent;
    private Fruit fruit;
    private FondJeu fondJeu;
    private GestureDetector gestureDetector;

    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 150;

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

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Direction cap = serpent.getCap();

        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && cap != Direction.EST) {
            serpent.setCap(Direction.OUEST);
            return true;
        }
        else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && cap != Direction.OUEST) {
            serpent.setCap(Direction.EST);
            return true;
        }

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && cap != Direction.SUD) {
            serpent.setCap(Direction.NORD);
            return true;
        }
        else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && cap != Direction.NORD) {
            serpent.setCap(Direction.SUD);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        int x = (int)e.getX();
        int y = (int)e.getY();
        Direction cap = serpent.getCap();


        if (x >= serpent.getX() && (cap == Direction.NORD || cap == Direction.SUD))
            serpent.setCap(Direction.EST);
        else if (x <= serpent.getX() && (cap == Direction.NORD || cap == Direction.SUD))
            serpent.setCap(Direction.OUEST);
        else if (y >= serpent.getY() && (cap == Direction.OUEST || cap == Direction.EST))
            serpent.setCap(Direction.SUD);
        else if (y <= serpent.getY() && (cap == Direction.EST || cap == Direction.OUEST))
            serpent.setCap(Direction.NORD);


        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    // Gère les touchés sur l'écran
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gestureDetector == null)
            gestureDetector = new GestureDetector(getContext(), this);
        return gestureDetector.onTouchEvent(event);  // On retourne vrai pour indiquer qu'on a géré l'évènement
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
