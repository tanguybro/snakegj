package plan;

public enum Direction {
    EST(1, 0), NORD(0, 1), OUEST (-1, 0), SUD(0, -1);

    private int dx, dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
