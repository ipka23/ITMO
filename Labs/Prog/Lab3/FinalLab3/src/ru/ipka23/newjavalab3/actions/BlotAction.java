package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.inkobject.Blot;
import ru.ipka23.newjavalab3.interfaces.IBlot;
import ru.ipka23.newjavalab3.page.NotebookPage;

public class BlotAction implements IBlot {


    @Override
    public void makeTheBlot(Neznayka neznayka, Blot blot, NotebookPage notebookPage) {
        if (Math.random() <= 0.5) {
            blot.setBlotOnThePage();
            notebookPage.setBlotOnThePage();
            System.out.print(neznayka.getName() + " поставил кляксу" + " на " + notebookPage + ", и ");
        }
        else {
            System.out.println(neznayka.getName() + " не поставил кляксу" + " на " + notebookPage);
        }
    }


    @Override
    public void lickTheBlot(Neznayka neznayka, Blot blot) {
        if (blot.isOnThePage()) {
            blot.setLongTail();
            System.out.println(neznayka.getName() + " слизал "  + blot.getName() + " языком, из-за этого клякса получилась с длинным хвостом.");
        }
    }
}
