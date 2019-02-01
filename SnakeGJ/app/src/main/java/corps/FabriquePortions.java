package corps;

import android.widget.ImageView;

public class FabriquePortions {
    public static Portion créer(int x, int y, Portion précédent, ImageView forme) {
        return new Portion(précédent, x, y, forme);
    }
}
