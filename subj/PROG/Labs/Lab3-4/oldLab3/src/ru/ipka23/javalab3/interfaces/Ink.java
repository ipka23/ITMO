package ru.ipka23.javalab3.interfaces;

import ru.ipka23.javalab3.enums.InkObject;

public interface Ink {
    void setTypeOfInkObject(InkObject inkObject);
    InkObject getTypeOfInkObject();
}
