package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Experience;
import ru.ipka23.newjavalab3.classes.Letter;
import ru.ipka23.newjavalab3.page.NotebookPage;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.List;

public interface Writable {
    void write(Neznayka neznayka, List<Letter> letters, Experience experience);
    void writeLetter(Neznayka neznayka, List<NotebookPage> pages, Letter letter);
    void cantWriteLetter(Neznayka neznayka, List<NotebookPage> pages, Letter letter);
    void startWriting(Neznayka neznayka, Notebook notebook);
    void finishWriting(Neznayka neznayka, Notebook notebook);

}
