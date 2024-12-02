package ru.ipka23.javalab3.classes;

import ru.ipka23.javalab3.enums.InkObject;
import ru.ipka23.javalab3.interfaces.Ink;

public class TypeOfInkObject implements Ink {
    private InkObject inkObject;

    public InkObject getInkObject(InkObject inkObject) {
        return inkObject;
    }

    public void setInkObject(InkObject inkObject) {
        this.inkObject = inkObject;
    }
}
