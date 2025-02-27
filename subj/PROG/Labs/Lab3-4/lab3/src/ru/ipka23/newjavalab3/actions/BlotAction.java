package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.inkobject.Blot;
import ru.ipka23.newjavalab3.interfaces.IBlot;
import ru.ipka23.newjavalab3.interfaces.StoryTeller;
import ru.ipka23.newjavalab3.page.NotebookPage;

import java.util.HashMap;
import java.util.Map;

public class BlotAction implements IBlot, StoryTeller {
    private Neznayka neznayka;
    private final Map<NotebookPage, Blot> blotMap = new HashMap<>();

    @Override
    public void makeTheBlot(Neznayka neznayka, Blot blot, NotebookPage notebookPage) {
        this.neznayka = neznayka;
        double chance = Math.random();
        if (chance <= 0.5) {
            blot.setBlotOnThePage();
            notebookPage.setBlotOnThePage();
            blotMap.put(notebookPage, blot);
        }
        else {
            blotMap.put(notebookPage, null);
        }
    }

    @Override
    public void lickTheBlot(Blot blot) {
        if (blot.isOnThePage()) {
            blot.setLongTail();
            blot.changeName("комета");
        }
    }

    @Override
    public void tell() {
        // пробегаемся по всем парам в Hash Map и сохраняем значения игры и статуса в соответствующие переменные
        for (Map.Entry<NotebookPage, Blot> entry : blotMap.entrySet()) {
            NotebookPage notebookPage = entry.getKey();
            Blot blot = entry.getValue();
            if (blot == null) {
                System.out.println(neznayka.getName() + " не поставил кляксу на " + notebookPage + ".");
            }
            else {
                System.out.println(neznayka.getName() + " поставил кляксу на " + notebookPage + ".");
                if (blot.isOnThePage()) {
                    System.out.println(neznayka.getName() + " слизал " + blot.getName() + " языком, из-за этого клякса получилась с длинным хвостом.");
                }
                if (blot.hasLongTail()) {
                    System.out.println("Кляксы с длинным хвостом он называл " + blot.getName() + ".");

                }
            }
        }
    }
}
