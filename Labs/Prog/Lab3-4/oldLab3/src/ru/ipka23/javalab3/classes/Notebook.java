package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.abstractclasses.AbstractPageObject;

import java.util.Arrays;
import java.util.List;

public class Notebook extends AbstractPageObject<NotebookPage> {
    private String notebookName = "тетрадочка";

    public Notebook(List<NotebookPage> pages) {
        super(pages);
    }

    public String getNotebookName() {
        return notebookName;
    }

    public static List<NotebookPage> setNotebookPages(NotebookPage... notebookPages) {
        return Arrays.asList(notebookPages);
    }
}
