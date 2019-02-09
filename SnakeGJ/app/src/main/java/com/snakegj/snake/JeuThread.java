package com.snakegj.snake;
import android.graphics.Canvas;

public class JeuThread extends Thread {


        // on définit arbitrairement le nombre d'images par secondes à 30
        private final static int IMG_PAR_SDE = 60;

        // si on veut X images en 1 seconde, soit en 1000 ms,
        // on doit en afficher une toutes les (1000 / X) ms.
        private final static int FREQUENCE = 1000 / IMG_PAR_SDE;

        private final JeuVue view;
        private boolean running = false; // état du thread


        public JeuThread(JeuVue view) {
            this.view = view;
        }


        public void setRunning(boolean run) {
            running = run;
        }


        @Override
        public void run()
        {
            long startTime;
            long sleepTime;

            while (running)
            {
                startTime = System.currentTimeMillis();

                // mise à jour du déplacement du snake
                synchronized (view.getHolder()) {
                    view.update();
                }

                // Rendu de l'image, tout en vérrouillant l'accès car nous
                // y accédons à partir d'un processus distinct
                Canvas c = null;
                try {
                    c = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()) {view.doDraw(c);}
                }
                finally
                {
                    if (c != null) {view.getHolder().unlockCanvasAndPost(c);}
                }

                // Calcul du temps de pause, et pause si nécessaire
                // afin de ne réaliser le travail ci-dessus que X fois par secondes
                sleepTime = FREQUENCE-(System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime >= 0) {sleep(sleepTime);}
                }
                catch (Exception e) {}
            }
        }

}
