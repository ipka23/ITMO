package ru.ipka23.javalab3.abstractclasses;

import ru.ipka23.javalab3.classes.BookPage;
import ru.ipka23.javalab3.classes.Letter;
import ru.ipka23.javalab3.classes.Notebook;
import ru.ipka23.javalab3.classes.NotebookPage;
import ru.ipka23.javalab3.enums.*;
import ru.ipka23.javalab3.interfaces.Playable;
import ru.ipka23.javalab3.interfaces.Readable;
import ru.ipka23.javalab3.interfaces.Sitable;
import ru.ipka23.javalab3.interfaces.Writeable;

import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCharacter implements Playable, Sitable, Readable, Writeable {
    private String name;
    private Frequency readingFrequency;
    private String benefitStatus;
    private static int experience = 0;
    private boolean readingIsStarted;
    private boolean writingIsStarted;
    private boolean readingIsFinished;
    private boolean writingIsFinished;
    public AbstractCharacter(String name) {
        this.name = name;
    }


    public void sit(ObjectForSitting objectForSitting){
        if (ObjectForSitting.TABLE == objectForSitting){
            System.out.print(getName() + " сел за " + objectForSitting.getObjectForSittingName() + " и");
        }
        else {
            System.out.print(getName() + " сел на " + objectForSitting.getObjectForSittingName() + " и");
        }
    }

    public void play(Game game, boolean b) {
        if (b){
            System.out.println(getName() + " играет в " + game.getGame());
        }
        else {
            System.out.println(getName() + " не играет в " + game.getGame());
        }
    }

//
//    @Override
//    public void startReading(List<Page> pages) {
//        this.readingIsStarted = true;
//        System.out.println(" начал читать.");
//    }
    @Override
    public boolean finishReading(List<BookPage> pages) {
        this.readingIsFinished = true;
        System.out.print("Покончив с чтением, ");
        return true;
    }
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
    @Override
    public void read(List<BookPage> pages, double chance) {
        if (Math.random() < chance){
            for (BookPage page : pages) {
                page.setReadFlag();
            }
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, " + "но сегодня прочитал две" + getBenefit());
        }
        else {
            for (BookPage page : pages) {
                page.setReadFlag();
                break;
            }
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, но" + getBenefit());

        }
    }
    public String getBenefit(){
        benefitStatus = "большая польза";
        return " и от этого была, конечно, " + benefitStatus + ".";
    }
    @Override
    public String toString() {
        return "Персонаж: " + name;
    }


    @Override
    public void  startReading(List<BookPage> pages) {
        readingIsStarted = true;
        System.out.println(" начал читать.");
    }
    @Override
    public void writeLetter(List<NotebookPage> pages, Letter letter, boolean b) {
        if (!b) {
            enhanceTheExperience();
            System.out.print("Писал " + getName() + " не " + letter.getLetterType().getLetter());
        }
        else {
            enhanceTheExperience();
            System.out.println(", а " + letter.getLetterCharacteristic().getLetter() + " " + letter.getLetterType().getLetter() + ".");
        }
    }

    @Override
    public boolean startWriting(Notebook notebook) {
        this.writingIsFinished = false;
        this.writingIsStarted = true;
        if (readingIsFinished) {
            System.out.println(getName() + " начинал писать в " + notebook.getNotebookName() + ".");
        }
        else {
            System.out.println(getName() + " ещё не закончил читать!");
        }
        return true;
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
        if (getExperience() <= 3) {
            System.out.print(experience() + " у " + getName() + " выходили " + InkObject.KRIVULKA.getName() + " и " + InkObject.KRENDEL.getName());
        }
        else {
            System.out.println(", но " + tryHard() + " и постепенно выучился писать " + letters.get(0).getLetterType().getLetter() + " " + letters.get(0).getLetterCharacteristic().getLetter() + " и " + letters.get(1).getLetterType().getLetter() + " " + letters.get(1).getLetterCharacteristic().getLetter() + " буквы.");
        }
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
        return getName() + " очень старался";
    }
}
