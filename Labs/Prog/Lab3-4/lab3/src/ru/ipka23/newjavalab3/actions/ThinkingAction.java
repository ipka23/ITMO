package ru.ipka23.newjavalab3.actions;


import ru.ipka23.newjavalab3.classes.Effort;

import ru.ipka23.newjavalab3.page.NotebookPage;


public class ThinkingAction {
    public static void think() {
        if (Effort.patienceStatus() && Effort.doingHardWorkStatus() && (NotebookPage.doesItHasBlotOnEveryPage() || NotebookPage.doesItHasBlotAlmostOnEveryPage())) {
            System.out.println("терпение и труд помогут избавиться от комет.");
        }
    }
}
