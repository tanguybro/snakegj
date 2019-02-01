package serpent;

import android.widget.ImageView;

import corps.FabriquePortions;
import corps.Portion;

public class Serpent {
    private Portion[] portions;
    private ImageView forme;

    public Serpent(int nbPortions, int x, int y) {
        portions = new Portion[nbPortions];
        portions[0] = FabriquePortions.créer(x, y, null, forme);
        for (int i = 1; i <= nbPortions; ++i)
            portions[i] = FabriquePortions.créer(x - (i + 1), y, portions[i-1], forme);
    }

    public void déplacer(int xMax, int yMax) {
        for (int i = portions.length - 1; i >= 0; --i)
            portions[i].déplacer(xMax, yMax);
    }

    public void dessiner(ImageView[][] t) {
        for (Portion a : portions)
            a.dessiner(t);
    }
}
