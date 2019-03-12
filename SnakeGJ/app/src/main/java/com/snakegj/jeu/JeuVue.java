package com.snakegj.jeu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.snakegj.R;
import com.snakegj.plan.Direction;
import com.snakegj.popup.PopupFinPartie;
import com.snakegj.snake.elementsGraphiques.FondJeu;
import com.snakegj.snake.elementsGraphiques.Fruit;
import com.snakegj.snake.elementsGraphiques.Serpent;

public class JeuVue extends SurfaceView implements SurfaceHolder.Callback {
    private JeuThread jeuThread;
    private Serpent serpent;
    private Fruit fruit;
    private FondJeu fondJeu;
    private String pseudo;
    private Context context;
    private Jeu jeu;
    private static int score;

    public JeuVue(Context context, Jeu j, String nom) {
        super(context);
        getHolder().addCallback(this);
        jeuThread = new JeuThread(this);
        fruit = new Fruit();
        serpent = new Serpent();
        fondJeu = new FondJeu();
        score = 0;
        pseudo = nom;
        jeu = j;
        this.context = context;
    }

    public static int getScore() {
        return score;
    }

    public void finPartie() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Classement");
        database.child(pseudo).setValue(score);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(score > preferences.getInt("Meilleur Score", 0)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Meilleur Score", score);
            editor.commit();
        }
        Intent intent = new Intent(context, PopupFinPartie.class);
        context.startActivity(intent);
    }

    //dessine un écran de jeu
    public void doDraw(Canvas canvas) {
        if(canvas == null)
            return;
        canvas.drawColor(Color.WHITE);  // on efface l'écran, en blanc
        fondJeu.dessiner(canvas);
        serpent.dessiner(canvas);
        fruit.dessiner(canvas);
    }

    //gestion du serpent
    public void update() {
        serpent.deplacer();
        if(estSurFruit()) {
              serpent.manger();
              fruit.apparaitre(serpent);
              score++;
              jeu.modifScore("Score : " + score);
        }
        if(serpent.seTouche())
            finPartie();
    }

    public boolean estSurFruit() {
        return !(fruit.getX() >= serpent.getX() + serpent.getLargeurAnneau()
                || fruit.getX() + fruit.getLargeur() <= serpent.getX()
                || fruit.getY() >= serpent.getY() + serpent.getHauteurAnneau()
                || fruit.getY() + fruit.getHauteur() <= serpent.getY());
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
        fondJeu.modifierDimensions(w,h);
        serpent.redimensionner(getContext(), R.drawable.gilet_jaune, 10, 10);
        fruit.redimensionner(getContext(), R.drawable.police, 12, 15);
        fondJeu.redimensionner(getContext(), R.drawable.fond, 1, 1);
    }
}
