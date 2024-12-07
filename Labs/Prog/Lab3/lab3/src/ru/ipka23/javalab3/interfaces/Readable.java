package ru.ipka23.javalab3.interfaces;

import ru.ipka23.javalab3.classes.BookPage;
import ru.ipka23.javalab3.enums.Frequency;

import java.util.List;

public interface Readable {
    void read(List<BookPage> pages, double chance);
    void startReading(List<BookPage> pages);
    boolean finishReading(List<BookPage> pages);
    boolean isReading(List<BookPage> pages);
    void setReadingFrequency(Frequency frequency);
    String getReadingFrequency();
}
