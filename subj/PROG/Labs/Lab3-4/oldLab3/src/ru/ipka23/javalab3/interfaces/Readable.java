package ru.ipka23.javalab3.interfaces;

import ru.ipka23.javalab3.classes.BookPage;
import ru.ipka23.javalab3.enums.Frequency;

import java.util.List;

public interface Readable {
    void read(List<BookPage> pages);
    void startReading(List<BookPage> pages);
    void finishReading();
    boolean isReading(List<BookPage> pages);
    void setReadingFrequency(Frequency frequency);
    String getReadingFrequency();
}
