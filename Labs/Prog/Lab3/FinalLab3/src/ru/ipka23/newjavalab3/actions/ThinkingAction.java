package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Effort;

import ru.ipka23.newjavalab3.page.NotebookPage;

import java.util.List;

public class ThinkingAction {
    public void think(Neznayka neznayka, Effort effort, List<NotebookPage> notebookPages) {
        if (effort.patienceStatus() && effort.doingHardWorkStatus() && (NotebookPage.doesItHasBlotOnEveryPage() || NotebookPage.doesItHasBlotAlmostOnEveryPage())) {
            System.out.println("терпение и труд помогут избавиться от комет.");
        }
    }
}
