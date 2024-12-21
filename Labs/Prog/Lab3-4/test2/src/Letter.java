public class Letter implements Ink{
    private InkObject inkObject;

    public InkObject getInkObject() {
        return inkObject;
    }
    public void setInkObject(InkObject inkObject) {
        this.inkObject = inkObject;
    }


    public Letter(InkObject inkObject) {
        this.inkObject = inkObject;
    }
}
