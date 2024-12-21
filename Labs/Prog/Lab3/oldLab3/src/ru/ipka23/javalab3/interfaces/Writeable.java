package ru.ipka23.javalab3.interfaces;

import ru.ipka23.javalab3.classes.Letter;
import ru.ipka23.javalab3.classes.Notebook;
import ru.ipka23.javalab3.classes.NotebookPage;
import ru.ipka23.javalab3.enums.Frequency;
import ru.ipka23.javalab3.enums.InkObject;
import ru.ipka23.javalab3.enums.LetterType;

import java.util.List;

public interface Writeable {
    void canWriteLetter(NotebookPage notebookPage, Letter letter);
    void cantWriteLetter(NotebookPage notebookPage, Letter letter);
    void startWriting(Notebook notebook);
    boolean finishWriting();
    boolean isWriting(List<NotebookPage> pages);
    void write(List<Letter> letters);

}
