package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Experience;
import ru.ipka23.newjavalab3.classes.Letter;
import ru.ipka23.newjavalab3.enums.InkObject;
import ru.ipka23.newjavalab3.interfaces.StoryTeller;
import ru.ipka23.newjavalab3.interfaces.Writable;
import ru.ipka23.newjavalab3.page.NotebookPage;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.List;

public class WritingAction extends WriterAction implements Writable, StoryTeller {
    private boolean writingIsStarted;
    private boolean writingIsFinished;
    private Experience experience = new Experience();
    private List<Letter> letters;
    private Notebook notebook;
    private boolean wasAbleToWriteLetter;
    private Letter letter;
    private AbstractCharacter character;

    @Override
    public void write(Neznayka neznayka, List<Letter> letters, Experience experience) {
        this.experience = experience;
        setLettersList(letters);
        experience.enhance(2);
    }
    private void setLettersList(List<Letter> letters) {
        this.letters = letters;
    }
    private void setLetter(Letter letter) {
        this.letter = letter;
    }
    private void setCharacter(AbstractCharacter character) {
        this.character = character;
    }
    @Override
    public void cantWriteLetter(Neznayka neznayka, List<NotebookPage> pages, Letter letter) {
        setCharacter(neznayka);
        setLetter(letter);
        experience.enhance(2);
        wasAbleToWriteLetter = false;
        this.letter = letter;
    }
    @Override
    public void writeLetter(Neznayka neznayka, List<NotebookPage> pages, Letter letter){
        setCharacter(neznayka);
        setLetter(letter);
        experience.enhance(2);
        wasAbleToWriteLetter = true;
        this.letter = letter;
    }

    private void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }
    @Override
    public void startWriting(Neznayka neznayka, Notebook notebook) {
        setCharacter(neznayka);
        writingIsFinished = false;
        writingIsStarted = true;
        setNotebook(notebook);
    }


    @Override
    public void finishWriting(Neznayka neznayka, Notebook notebook) {
        writingIsStarted = false;
        writingIsFinished = true;
    }

    @Override
    public void tell() {
        if (!wasAbleToWriteLetter) {
            System.out.print("Писал " + character.getName() + " не " + letter.getLetterType().getLetter());
        }

        if (wasAbleToWriteLetter) {
            System.out.println(", а " + letter.getLetterCharacteristic().getLetter() + " " + letter.getLetterType().getLetter() + ".");
        }

        if (writingIsStarted) {
            System.out.println("Незнайка начинал писать в " + notebook.getNotebookName() + ".");
        }

        if (experience.get() <= 2) {
            System.out.println(experience.describe() + "у " + character.getName() + " выходили "
                    + InkObject.KRIVULKA.getName() + " и " + InkObject.KRENDEL.getName() + ".");
            System.out.println("И постепенно выучился писать "
                    + letters.get(0).getLetterType().getLetter() + " " + letters.get(0).getLetterCharacteristic().getLetter()
                    + " и " + letters.get(1).getLetterType().getLetter() + " " + letters.get(1).getLetterCharacteristic().getLetter() + " буквы.");
        }
    }

}
