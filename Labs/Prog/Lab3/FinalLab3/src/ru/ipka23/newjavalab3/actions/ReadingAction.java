package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.enums.Frequency;
import ru.ipka23.newjavalab3.interfaces.Readable;
import ru.ipka23.newjavalab3.page.BookPage;

import java.util.List;

public class ReadingAction implements Readable {
    private boolean readingIsStarted;
    private boolean readingIsFinished;
    private Frequency readingFrequency;


    @Override
    public void read(Neznayka neznayka, List<BookPage> pages) {
        if (Math.random() < 0.5){
            for (BookPage page : pages) {
                page.setReadFlag();
            }
            System.out.print("Читал он " + getReadingFrequency(neznayka) + " по страничке, " + "но сегодня прочитал две ");
        }
        else {
            for (BookPage page : pages) {
                page.setReadFlag();
                break;
            }
            System.out.print("Читал он " + getReadingFrequency(neznayka) + " по страничке, но ");
        }
    }


    @Override
    public void setReadingFrequency(Neznayka neznayka, Frequency frequency) {
        readingFrequency = switch (frequency) {
            case EVERY_DAY -> Frequency.EVERY_DAY;
            case EVERY_WEEK -> Frequency.EVERY_WEEK;
            case EVERY_YEAR -> Frequency.EVERY_YEAR;
            case EVERY_MONTH -> Frequency.EVERY_MONTH;
        };
    }


    @Override
    public boolean finishReading(Neznayka neznayka) {
        readingIsFinished = true;
        System.out.print("Покончив с чтением, ");
        return true;
    }


    @Override
    public void startReading(Neznayka neznayka) {
        readingIsStarted = true;
        System.out.println(neznayka.getName() + " начал читать.");
    }


    @Override
    public boolean isReading(Neznayka neznayka) {
        return readingIsStarted;
    }


    @Override
    public String getReadingFrequency(Neznayka neznayka) {
        return readingFrequency.getFrequency();
    }

}
