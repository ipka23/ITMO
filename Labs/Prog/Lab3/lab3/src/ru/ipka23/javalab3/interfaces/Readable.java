package ru.ipka23.javalab3.interfaces;

import ru.ipka23.javalab3.classes.Page;
import ru.ipka23.javalab3.enums.Frequency;

import java.util.List;

public interface Readable {
    void read(List<Page> pages, double chance);
    void startReading(List<Page> pages);
    boolean finishReading(List<Page> pages);
    boolean isReading(List<Page> pages);
    void setReadingFrequency(Frequency frequency);
    String getReadingFrequency();
}
