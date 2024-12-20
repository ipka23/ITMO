package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.inkobject.Blot;
import ru.ipka23.newjavalab3.page.NotebookPage;

public interface IBlot {
    void makeTheBlot(Neznayka neznayka, Blot blot, NotebookPage notebookPage);
    void lickTheBlot(Blot blot);

}
