package ru.ipka23.newjavalab3.pageobjects;

import ru.ipka23.newjavalab3.page.NotebookPage;

import java.util.List;

public class Notebook extends AbstractPageObject<NotebookPage> {
    private String notebookName = "тетрадочка";

    public Notebook(List<NotebookPage> pages) {
        super(pages);
    }

    public String getNotebookName() {
        return notebookName;
    }

}

