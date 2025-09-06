package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.enums.*;
import ru.ipka23.javalab3.interfaces.Playable;
import ru.ipka23.javalab3.interfaces.Readable;
import ru.ipka23.javalab3.interfaces.Sitable;
import ru.ipka23.javalab3.interfaces.Writeable;

import java.util.List;

public class Neznayka implements Playable, Sitable, Readable, Writeable {
    private boolean isPlaying;
    private boolean isNotPlaying;
    private String name;
    private Frequency readingFrequency;
    private String benefitStatus;
    private static int experience = 0;
    private boolean readingIsStarted;
    private boolean readingIsFinished;
    private boolean writingIsStarted;
    private boolean writingIsFinished;
    private Game game;
    private String tryingHardStatus;
    private Notebook notebook;
    private Letter letter;
    private boolean writtenLetter;
    private NotebookPage notebookPage;
    private ObjectForSitting objectForSitting;
    private boolean isSitting;
    private List<Letter> letters;
    private double chanceToMakeBlot = Math.random();
    private Blot blot;
    private boolean lickedTheBlot;
    private Mood mood;
    public Neznayka() {
        this.name = "Незнайка";
    }

    @Override
    public void play(Game game) {
        isPlaying = true;
        isNotPlaying = false;
        this.game = game;

    }
    @Override
    public void dontPlay(Game game) {
        isPlaying = false;
        isNotPlaying = true;
        this.game = game;
    }

    public void makeBlot(Blot blot, NotebookPage page) {
        if (chanceToMakeBlot <= 0.5) {
            blot.setBlotOnThePage();
            page.setBlotOnThePage();
        }
        this.blot = blot;
        this.notebookPage = page;
    }

    private void setMood(Mood mood) {
        this.mood = mood;
    }
    public void lickTheBlot(Blot blot) {
        if (blot.isOnThePage()) {
            blot.setLongTail();
        }
        lickedTheBlot = true;
        this.blot = blot;
    }

    public void mood() {
        if (NotebookPage.isHasBlotOnEveryPage()) {
            mood = Mood.UPSET;
        }
        else if (NotebookPage.isHasBlotAlmostOnEveryPage()) {
            mood = Mood.GOOD;
        }
        else {
            mood = Mood.HAPPY;
        }
    }



    public void sit(ObjectForSitting objectForSitting){
        this.objectForSitting = objectForSitting;
        isSitting = true;
    }

    @Override
    public void finishReading() {
        this.readingIsFinished = true;
        readingIsStarted = false;
    }
    @Override
    public boolean isReading(List<BookPage> pages) {
        return readingIsStarted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setReadingFrequency(Frequency frequency) {
        switch (frequency) {
            case EVERY_DAY -> readingFrequency = Frequency.EVERY_DAY;
            case EVERY_WEEK -> readingFrequency = Frequency.EVERY_WEEK;
            case EVERY_YEAR -> readingFrequency = Frequency.EVERY_YEAR;
            case EVERY_MONTH -> readingFrequency = Frequency.EVERY_MONTH;
        }
    }
    @Override
    public String getReadingFrequency() {
        return readingFrequency.getFrequency();
    }
    private double chance;
    @Override
    public void read(List<BookPage> pages) {
        if (chance < 0.5){
            for (BookPage page : pages) {
                page.setReadFlag();
            }
        }
        else {
            for (BookPage page : pages) {
                page.setReadFlag();
                break;
            }
        }
    }
    public String getBenefit(){
        benefitStatus = "большая польза";
        return " и от этого была, конечно, " + benefitStatus + ".";
    }

    @Override
    public void  startReading(List<BookPage> pages) {
        readingIsStarted = true;
        readingIsFinished = false;
    }
    @Override
    public void canWriteLetter(NotebookPage notebookPage, Letter letter) {
        this.notebookPage = notebookPage;
        this.letter = letter;
        writtenLetter = true;
    }
    @Override
    public void cantWriteLetter(NotebookPage notebookPage, Letter letter){
        writtenLetter = false;
        this.notebookPage = notebookPage;
        this.letter = letter;
    }

    @Override
    public void startWriting(Notebook notebook) {
        this.writingIsFinished = false;
        this.writingIsStarted = true;
        this.notebook = notebook;

    }

    @Override
    public boolean finishWriting() {
        this.writingIsStarted = false;
        this.writingIsFinished = true;
        return true;
    }

    @Override
    public boolean isWriting(List<NotebookPage> pages) {
        return writingIsStarted;
    }

    @Override
    public void write(List<Letter> letters) {
        enhanceTheExperience();
        this.letters = letters;
    }
    public int getExperience() {
        return experience;
    }


    public String experience() {
        if (experience <= 3) {
            return "Первое время ";
        }
        else {
            return "с опытом";
        }
    }

    public void enhanceTheExperience() {
        experience++;
    }

    public String tryHard(){
        this.tryingHardStatus = name + " очень старался";
        return tryingHardStatus;
    }



    public void tell() {
        if (isPlaying) {
            System.out.println(getName() + " играет в " + game.getGame());
        }
        if (isNotPlaying) {
            System.out.print("Вместо того чтобы " + getName() + " играть в " + game.getGame() + ", ");
        }
        if (isSitting) {
            if (ObjectForSitting.TABLE == objectForSitting){
                System.out.print(getName() + " сел за " + objectForSitting.getObjectForSittingName() + " и");
            }
            else {
                System.out.print(getName() + " сел на " + objectForSitting.getObjectForSittingName() + " и");
            }
        }
        if (readingIsStarted) {
            System.out.println(" начал читать.");
        }
        if (chance < 0.5) {
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, " + "но сегодня прочитал две" + getBenefit());
        }
        if (chance >= 0.5) {
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, но" + getBenefit());
        }
        if (readingIsFinished) {
            System.out.print("Покончив с чтением, ");
        }
        if (writingIsStarted) {
            System.out.println(getName() + " начинал писать в " + notebook.getNotebookName() + ".");
        }
        if (writtenLetter) {
            enhanceTheExperience();
            System.out.print("Писал " + getName() + " не " + letter.getLetterType().getLetter());
        }
        if (!writtenLetter) {
            enhanceTheExperience();
            System.out.println(", а " + letter.getLetterCharacteristic().getLetter() + " " + letter.getLetterType().getLetter() + ".");
        }
        if (getExperience() <= 3) {
            System.out.print(experience() + " у " + getName() + " выходили " + InkObject.KRIVULKA.getName() + " и " + InkObject.KRENDEL.getName());
        }
        if (getExperience() > 3) {
            System.out.println(", но " + tryHard() + " и постепенно выучился писать " + letters.get(0).getLetterType().getLetter() + " " + letters.get(0).getLetterCharacteristic().getLetter() + " и " + letters.get(1).getLetterType().getLetter() + " " + letters.get(1).getLetterCharacteristic().getLetter() + " буквы.");
        }
        if (chanceToMakeBlot <= 0.5) {
            System.out.print(getName() + " поставил кляксу, и ");
        }
        if (chanceToMakeBlot > 0.5) {
            System.out.println(getName() + " не поставил кляксу");
        }
        if (lickedTheBlot) {
            blot.blotWithLongTail();
        }
        if (!lickedTheBlot) {
            blot.regularBlot();
        }
        if (mood == Mood.UPSET) {
            System.out.println(getName() + " приуныл.");
        }
        if (mood == Mood.GOOD) {
            System.out.println(getName() + " не унывал.");
        }
        if (mood == Mood.HAPPY) {
            System.out.println(getName() + " счастлив.");
        }
    }
}