package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Experience;
import ru.ipka23.newjavalab3.classes.Letter;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.List;

public interface Writable {
    void write(List<Letter> letters, Experience experience);
    void writeLetter(Neznayka neznayka, Letter letter);
    void cantWriteLetter(Neznayka neznayka, Letter letter);
    void startWriting(Neznayka neznayka, Notebook notebook);
}
