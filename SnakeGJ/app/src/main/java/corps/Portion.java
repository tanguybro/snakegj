package corps;

import android.widget.ImageView;

public class Portion {
    private Portion précédent;
    private int x, y;
    private ImageView forme;

    public Portion(int x, int y, ImageView forme) {
        this(null, x, y, forme);
    }

    public Portion(Portion précédent, int x, int y, ImageView forme) {
        this.précédent = précédent;
        placerA(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void placerA(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ImageView getForme() {
        return forme;
    }

    public void dessiner(ImageView[][] t) {
        t[x][y] = getForme();
    }

    public void déplacer(int xMax, int yMax) {
        placerA(précédent.x, précédent.y);
    }
}
