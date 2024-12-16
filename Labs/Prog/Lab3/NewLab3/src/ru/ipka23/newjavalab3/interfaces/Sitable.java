package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.ObjectForSitting;

public interface Sitable extends StoryTeller {
    void sit(AbstractCharacter abstractCharacter, ObjectForSitting objectForSitting);
    boolean getSittingStatus();



    default String getCurrentState(){
        if (getSittingStatus()){
            return "сидит";
        }
        else{
            return "не сидит";
        }
    }
    @Override
    default void tell(){
        System.out.println(getCurrentState());;
    }

}
