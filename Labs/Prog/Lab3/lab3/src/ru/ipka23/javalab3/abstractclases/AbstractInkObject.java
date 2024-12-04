package ru.ipka23.javalab3.abstractclases;

import ru.ipka23.javalab3.enums.InkObject;
import ru.ipka23.javalab3.interfaces.Ink;

public abstract class AbstractInkObject implements Ink {

    protected InkObject inkObject;
    protected String name;
    public AbstractInkObject(String name){
        this.name = name;
        System.out.println(getName() + " создана");
    }
    @Override
    public InkObject getTypeOfInkObject() {
        return inkObject;
    }
    @Override
    public void setTypeOfInkObject(InkObject inkObject) {
        this.inkObject = inkObject;
    }

    public AbstractInkObject(String name, InkObject inkObject){
        this.name = name;
        this.inkObject = inkObject;
        System.out.println(getName() + " создана");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }




}