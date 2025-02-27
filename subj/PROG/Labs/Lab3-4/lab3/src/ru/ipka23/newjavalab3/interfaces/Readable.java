package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.enums.Frequency;
import ru.ipka23.newjavalab3.page.BookPage;

import java.util.List;

public interface Readable {
    void read(Neznayka neznayka, List<BookPage> pages);
    void startReading();
    void finishReading();
    void setReadingFrequency(Frequency frequency);
    String getReadingFrequency();
}
