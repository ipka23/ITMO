package utility;

public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean hasNextLine();
    void printError(Object obj);
    void printPrompt();
}