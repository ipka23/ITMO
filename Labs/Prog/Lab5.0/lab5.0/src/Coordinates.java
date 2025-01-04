public class Coordinates implements Validatable {
    private int x;
    private float y;

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }

    @Override
    public boolean validate() {
        return true;
    }
}