package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.ObjectForSitting;
import ru.ipka23.newjavalab3.interfaces.Sitable;

public class SittingAction implements Sitable {
    private boolean isSitting = false;


    @Override
    public void sit(AbstractCharacter abstractCharacter, ObjectForSitting objectForSitting){
        if (ObjectForSitting.TABLE == objectForSitting){
            isSitting = true;
            System.out.print(abstractCharacter.getName() + " сел за " + objectForSitting.getObjectForSittingName() + ", ");
        }
        else {
            isSitting = true;
            System.out.print(abstractCharacter.getName() + " сел на " + objectForSitting.getObjectForSittingName() + ", ");
        }
    }


    @Override
    public boolean getSittingStatus() {
        return isSitting;
    }

    @Override
    public String tell() {
        if (isSitting) {
            return "сидит";
        }
        else {
            return "не сидит";
        }
    }
}