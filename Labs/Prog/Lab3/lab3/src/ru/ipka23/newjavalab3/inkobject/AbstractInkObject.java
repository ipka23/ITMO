package ru.ipka23.newjavalab3.inkobject;

import ru.ipka23.newjavalab3.enums.InkObject;

public abstract class AbstractInkObject{
    protected InkObject inkObject;
    protected String name;


    public AbstractInkObject(String name, InkObject inkObject){
        this.name = name;
        this.inkObject = inkObject;
    }



    public InkObject getTypeOfInkObject() {
        return inkObject;
    }



    public void setTypeOfInkObject(InkObject inkObject) {
        this.inkObject = inkObject;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "объект из чернил: " + getName();
    }

}