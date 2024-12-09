package ru.ipka23.newjavalab3.classes;

import ru.ipka23.newjavalab3.characters.Neznayka;

public class Effort {
    private boolean tryingHard;
    private boolean hasPatience;
    private boolean doingHardWork;


    public void isTryingHard(Neznayka neznayka) {
        this.tryingHard = true;
    }


    public void tryingHardStatus(Neznayka neznayka) {
        if (tryingHard) {
            System.out.println(", но " + neznayka.getName() + " очень старался ");
        }
        else {
            System.out.println(" " + neznayka.getName() + " совсем не старался ");
        }
    }


    public void setPatience(Neznayka neznayka) {
        this.hasPatience = true;
    }


    public boolean patienceStatus() {
        return hasPatience;
    }


    public void isDoingHardWork(Neznayka neznayka) {
        this.doingHardWork = true;
    }


    public boolean doingHardWorkStatus() {
        return doingHardWork;
    }
}
