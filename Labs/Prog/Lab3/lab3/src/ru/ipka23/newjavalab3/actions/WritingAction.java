package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Effort;
import ru.ipka23.newjavalab3.classes.Experience;
import ru.ipka23.newjavalab3.classes.Letter;
import ru.ipka23.newjavalab3.enums.InkObject;
import ru.ipka23.newjavalab3.interfaces.StoryTeller;
import ru.ipka23.newjavalab3.interfaces.Writable;
import ru.ipka23.newjavalab3.pageobjects.Notebook;

import java.util.List;

public class  WritingAction implements Writable, StoryTeller {
    private boolean writingIsStarted;
    private boolean writingIsFinished;
    private Experience experience = new Experience();
    private List<Letter> letters;
    private Notebook notebook;
    private boolean wasAbleToWriteLetter;
    private boolean wasNotAbleToWriteLetter;
    private Letter letter;
    private AbstractCharacter character;
    private boolean wasAbleToWriteBeautiflLetter;


    @Override
    public void write(List<Letter> letters, Experience experience) {
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
    public void cantWriteLetter(Neznayka neznayka, Letter letter) {
        setCharacter(neznayka);
        setLetter(letter);
        experience.enhance(2);
        wasNotAbleToWriteLetter = true;
        this.letter = letter;
    }
    @Override
    public void writeLetter(Neznayka neznayka, Letter letter){
        setCharacter(neznayka);
        setLetter(letter);
        experience.enhance(2);
        wasAbleToWriteLetter = true;
        this.letter = letter;
    }
    public void writeBeautifulLetters(Neznayka neznayka, List<Letter> letters){
        setCharacter(neznayka);
        this.letters = letters;
        experience.enhance(2);
        if (experience.get() > 2) {
            wasAbleToWriteBeautiflLetter = true;
        }
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
    public void tell() {
        if (writingIsStarted) {
            System.out.println(character.getName() + " начинал писать в " + notebook.getNotebookName() + ".");
        }
        if (wasNotAbleToWriteLetter) {
            System.out.print("Писал " + character.getName() + " не "
                    + letter.getLetterType().getLetter());
        }

        if (wasAbleToWriteLetter) {
            wasNotAbleToWriteLetter = false;
            System.out.print(", а " + letter.getLetterCharacteristic().getLetter() + " "
                    + letter.getLetterType().getLetter() + ".");
        }

        System.out.println();

        if (experience.get() <= 4) {
            System.out.print("Первое время у " + character.getName() + " выходили "
                    + InkObject.KRIVULKA.getName() + " и " + InkObject.KRENDEL.getName());
        }

        if (Effort.istryingHard) {
            System.out.print(", но " + character.getName() + " очень старался ");
        }
        if (wasAbleToWriteBeautiflLetter) {
            System.out.println("и постепенно выучился писать " + letters.get(0).getLetterType().getLetter() + " " + letters.get(0).getLetterCharacteristic().getLetter() + " и " + letters.get(1).getLetterType().getLetter() + " " + letters.get(1).getLetterCharacteristic().getLetter() + " буквы.");
        }
    }


}
