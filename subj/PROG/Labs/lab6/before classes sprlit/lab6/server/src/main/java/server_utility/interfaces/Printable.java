package server_utility.interfaces;

import java.io.IOException;

public interface Printable {
    void println(Object o);
    void print(Object o) throws IOException;
    void printPrompt() throws IOException;
}
