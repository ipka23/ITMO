package ru.ipka23.javalab3.interfaces;

import ru.ipka23.javalab3.classes.Page;
import ru.ipka23.javalab3.enums.Frequency;

import java.util.List;

public interface Writeable {
    void write(List<Page> pages);
    boolean startWriting();
    boolean finishWriting();
    boolean isWriting(List<Page> pages);

}
