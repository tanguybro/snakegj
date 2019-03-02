package com.snakegj.snake;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.snakegj.PopupFinPartie;
import com.snakegj.R;
import com.snakegj.plan.Direction;
import com.snakegj.snake.elementsGraphiques.Fruit;
import com.snakegj.snake.elementsGraphiques.Serpent;

public class JeuVue extends SurfaceView implements SurfaceHolder.Callback {
    private JeuThread jeuThread;
    private Serpent serpent;
    private Fruit fruit;
    private String pseudo;
    private Context context;
    private static int score;
    private Paint paint;
    private static int hauteurEcran, largeurEcran;

    // création de la surface de dessin
    public JeuVue(Context context, String nom) {
        super(context);
        getHolder().addCallback(this);
        jeuThread = new JeuThread(this);
        fruit = new Fruit();
        serpent = new Serpent();
        score = 0;
        pseudo = nom;
        paint = new Paint();
        this.context = context;
    }

    public static int getHauteurEcran() {
        return hauteurEcran;
    }

    public static int getLargeurEcran() {
        return largeurEcran;
    }

    public static int getScore() {
        return score;
    }

    public void finPartie() {
        if(pseudo != null) {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
            database.child(pseudo).setValue(score);
        }

        //affiche la popup
        Intent intent = new Intent(context, PopupFinPartie.class);
        context.startActivity(intent);

    }

    //dessine un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas == null)
            return;
        canvas.drawColor(Color.WHITE);  // on efface l'écran, en blanc
        serpent.dessiner(canvas);
        fruit.dessiner(canvas);
        paint.setTextSize(25);
        paint.setColor(Color.GRAY);
        canvas.drawText("Score : " + score, 10, 50, paint);
    }

    //gestion du serpent
    public void update() {
        serpent.deplacer();
        if(!estPasSurFruit()) {
              serpent.manger();
              fruit.apparaitre();
              score++;
        }
        if(serpent.seTouche()) {
            finPartie();
            Log.d("touche", "coule");
        }

    }

    public boolean estPasSurFruit() {  /** A CHANGER */
        return fruit.getX() >= serpent.getX() + serpent.getLargeurAnneau()
                || fruit.getX() + fruit.getLargeur() <= serpent.getX()
                || fruit.getY() >= serpent.getY() + serpent.getHauteurAnneau()
                || fruit.getY() + fruit.getHauteur() <= serpent.getY();
    }

    // Gère les touchés sur l'écran
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

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        hauteurEcran = h;
        largeurEcran = w;
        serpent.redimensionner(getContext(), R.drawable.gilet_jaune, 10, 10);
        fruit.redimensionner(getContext(), R.drawable.ball, 12, 15);
        fruit.apparaitre();
    }
}
