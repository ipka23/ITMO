package utility;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CommandHistory {
    private LinkedList<String> history = new LinkedList<>();
    private ListIterator<String> iterator = history.listIterator();

    public CommandHistory() {}

    public void push(String command) {
        history.addLast(command);
    }

    public String getPrevious() {
        if (iterator.hasPrevious()) {
            return iterator.previous();
        }
        return null;
    }

    public String getNext() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }


    public void resetIterator() {
        iterator = history.listIterator(history.size());
    }
}