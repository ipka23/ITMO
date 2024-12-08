package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Effort;
import ru.ipka23.newjavalab3.classes.Experience;
import ru.ipka23.newjavalab3.classes.Letter;
import ru.ipka23.newjavalab3.enums.InkObject;
import ru.ipka23.newjavalab3.interfaces.Writable;
import ru.ipka23.newjavalab3.page.NotebookPage;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.List;

public class WritingAction implements Writable {
    private boolean writingIsStarted;
    private boolean writingIsFinished;
    private Effort effort;
    private Experience experience = new Experience();
    private ReadingAction readingAction;


    @Override
    public void write(Neznayka neznayka, List<Letter> letters, Experience experience) {
        this.experience = experience;
        experience.enhance(2);
        if (experience.get() <= 2) {
            System.out.print(experience.describe() + "у " + neznayka.getName() + " выходили " + InkObject.KRIVULKA.getName() + " и " + InkObject.KRENDEL.getName());
        }
        else {
            System.out.println("и постепенно выучился писать " + letters.get(0).getLetterType().getLetter() + " " + letters.get(0).getLetterCharacteristic().getLetter() + " и " + letters.get(1).getLetterType().getLetter() + " " + letters.get(1).getLetterCharacteristic().getLetter() + " буквы.");
        }
    }


    @Override
    public void writeLetter(Neznayka neznayka, List<NotebookPage> pages, Letter letter, boolean b){
        if (!b) {
            experience.enhance(2);
            System.out.print("Писал " + neznayka.getName() + " не " + letter.getLetterType().getLetter());
        }
        else {
            experience.enhance(2);
            System.out.println(", а " + letter.getLetterCharacteristic().getLetter() + " " + letter.getLetterType().getLetter() + ".");
        }
    }


    @Override
    public void startWriting(Neznayka neznayka, Notebook notebook) {
        writingIsFinished = false;
        writingIsStarted = true;
        System.out.println(neznayka.getName() + " начинал писать в " + notebook.getNotebookName() + ".");
    }


    @Override
    public void finishWriting(Neznayka neznayka, Notebook notebook) {
        writingIsStarted = false;
        writingIsFinished = true;
        System.out.println(neznayka.getName() + " закончил писать в " + notebook.getNotebookName() + ".");
    }
}
