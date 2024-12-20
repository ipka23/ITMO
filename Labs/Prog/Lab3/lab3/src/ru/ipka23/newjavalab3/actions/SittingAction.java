package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.ObjectForSitting;
import ru.ipka23.newjavalab3.interfaces.Sitable;
import ru.ipka23.newjavalab3.interfaces.StoryTeller;

public class SittingAction implements Sitable, StoryTeller {
    protected boolean isSitting = false;
    protected AbstractCharacter character;
    protected ObjectForSitting objectForSitting;


    @Override
    public void sit(AbstractCharacter abstractCharacter, ObjectForSitting objectForSitting){
        character = abstractCharacter;
        this.objectForSitting = objectForSitting;
        isSitting = true;
    }

    @Override
    public void tell() {
        if (ObjectForSitting.TABLE == objectForSitting){
            System.out.print(character.getName() + " сел за " + objectForSitting.getObjectForSittingName() + ", ");
        }
        else {
            System.out.print(character.getName() + " сел на " + objectForSitting.getObjectForSittingName() + ", ");
        }
    }



}