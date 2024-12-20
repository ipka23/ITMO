package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.ObjectForSitting;

public interface Sitable{
    void sit(AbstractCharacter abstractCharacter, ObjectForSitting objectForSitting);
}
