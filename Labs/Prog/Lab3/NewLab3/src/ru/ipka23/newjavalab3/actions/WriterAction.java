package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.interfaces.*;

public class WriterAction implements StoryTeller {

    @Override
    public void tell(){
        System.out.println("-------------");
    }
}
