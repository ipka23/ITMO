public class Main {
    public static void main(String[] args) {
        Letter letter = new Letter(InkObject.LETTER);
        InkObject typeOfInkObject = letter.getInkObject();
        System.out.println(typeOfInkObject);
    }
}
