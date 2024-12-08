package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.characters.Neznayka;
import ru.ipka23.newjavalab3.enums.Frequency;
import ru.ipka23.newjavalab3.page.BookPage;

import java.util.List;

public interface Readable {
    void read(Neznayka neznayka, List<BookPage> pages);
    void startReading(Neznayka neznayka);
    boolean finishReading(Neznayka neznayka);
    void setReadingFrequency(Neznayka neznayka, Frequency frequency);
    String getReadingFrequency(Neznayka neznayka);
    boolean isReading(Neznayka neznayka);
}
