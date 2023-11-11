public class Tile {
    private int x, y;

    public Tile() {}

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (getClass() != o.getClass()) { return false; }

        Tile t = (Tile) o;

        if (this.x != t.x) { return false; }
        if (this.y != t.y) { return false; }

        return true;
    }

    public int hashCode() { return x * 31 + y; }

    public String toString() { return String.format("Tile[x=%d, y=%d]", x, y); }
}
