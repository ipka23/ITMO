package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.enums.InkObject;

public interface Ink {
    void setTypeOfInkObject(InkObject inkObject);
    InkObject getTypeOfInkObject();
}