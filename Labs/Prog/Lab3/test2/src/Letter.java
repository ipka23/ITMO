public class Letter implements Ink{
    private InkObject inkObject;

    public Letter(InkObject inkObject) {
        this.inkObject = inkObject;
    }

    @Override
    public void setTypeOfInkObject(InkObject inkObject) {
        this.inkObject = inkObject;
    }
    @Override
    public InkObject getTypeOfInkObject() {
        return inkObject;
    }
}
