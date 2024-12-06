package ru.ipka23.javalab3.abstractclasses;

import ru.ipka23.javalab3.classes.Page;
import ru.ipka23.javalab3.enums.Frequency;
import ru.ipka23.javalab3.enums.Game;
import ru.ipka23.javalab3.enums.ObjectForSitting;
import ru.ipka23.javalab3.interfaces.Playable;
import ru.ipka23.javalab3.interfaces.Readable;
import ru.ipka23.javalab3.interfaces.Sitable;
import ru.ipka23.javalab3.interfaces.Writeable;

import java.util.List;

public abstract class AbstractCharacter implements Playable, Sitable, Readable, Writeable {
    private String name;
    private Frequency readingFrequency;
    private String benefitStatus;
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
    public boolean finishReading(List<Page> pages) {
        this.readingIsFinished = true;
        return true;
    }
    public boolean isReading(List<Page> pages) {
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
    public void read(List<Page> pages, double chance) {
        if (Math.random() < chance){
            for (Page page : pages) {
                page.setReadFlag();
            }
            finishReading(pages);
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, " + "но сегодня прочитал две" + getBenefit());
        }
        else {
            for (Page page : pages) {
                page.setReadFlag();
                System.out.println("Читал он " + getReadingFrequency() + " по страничке, но" + getBenefit());
                break;
            }
            finishReading(pages);
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
    public void  startReading(List<Page> pages) {
        readingIsStarted = true;
        System.out.println(" начал читать.");
    }
    @Override
    public void write(List<Page> pages) {

    }

    @Override
    public boolean startWriting() {
        this.writingIsFinished = false;
        this.writingIsStarted = true;
        System.out.println("Покончив с чтением ");
        return true;
    }

    @Override
    public boolean finishWriting() {
        this.writingIsStarted = false;
        this.writingIsFinished = true;
        return true;
    }

    @Override
    public boolean isWriting(List<Page> pages) {
      return writingIsStarted;
    }
}
