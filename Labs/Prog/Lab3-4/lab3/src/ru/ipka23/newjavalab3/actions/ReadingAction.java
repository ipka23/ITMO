package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.classes.Benefit;
import ru.ipka23.newjavalab3.enums.Frequency;
import ru.ipka23.newjavalab3.interfaces.Readable;
import ru.ipka23.newjavalab3.interfaces.StoryTeller;
import ru.ipka23.newjavalab3.page.BookPage;

import java.util.ArrayList;
import java.util.List;

public class ReadingAction implements Readable, StoryTeller {
    private boolean readingIsStarted;
    private boolean readingIsFinished;
    private Frequency readingFrequency;
    private Neznayka neznayka;
    private List<BookPage> pages = new ArrayList<>();
    private double chance = Math.random();

    @Override
    public void read(Neznayka neznayka, List<BookPage> pages) {
        this.pages = pages;
        this.neznayka = neznayka;
        setReadingFrequency(Frequency.EVERY_DAY);
        if (chance < 0.5){
            Benefit.setDoubleBenefit();
            for (BookPage page : pages) {
                page.setReadFlag();
            }
        }
        else {
            Benefit.setBenefit();
            for (BookPage page : pages) {
                page.setReadFlag();
                break;
            }
        }
    }


    @Override
    public void setReadingFrequency(Frequency frequency) {
        readingFrequency = switch (frequency) {
            case EVERY_DAY -> Frequency.EVERY_DAY;
            case EVERY_WEEK -> Frequency.EVERY_WEEK;
            case EVERY_YEAR -> Frequency.EVERY_YEAR;
            case EVERY_MONTH -> Frequency.EVERY_MONTH;
        };
    }


    @Override
    public void finishReading() {
        readingIsFinished = true;
    }


    @Override
    public void startReading() {
        readingIsStarted = true;
    }


    @Override
    public String getReadingFrequency() {
        return readingFrequency.getFrequency();
    }

    @Override
    public void tell() {
        if (readingIsStarted) {
            System.out.println("и начал читать.");
        }

        if (chance < 0.5){
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, " + "но сегодня прочитал две " + Benefit.get());
        }
        if (chance >= 0.5){
            System.out.println("Читал он " + getReadingFrequency() + " по страничке, но " + Benefit.get());
        }
        if (readingIsFinished) {
            System.out.print("Покончив с чтением, ");
        }
    }
}
