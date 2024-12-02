package ru.ipka23.javalab3.classes;
import ru.ipka23.javalab3.abstractclases.AbstractAction;
import ru.ipka23.javalab3.abstractclases.AbstractBlot;


public class Lick extends AbstractAction {
    private AbstractBlot blot;
    public Lick(AbstractBlot blot) {
        this.blot = blot;
    }
    public AbstractBlot getBlotName() {
        return blot;
    }

    public void setBlot(AbstractBlot blot) {
        this.blot = blot;
    }



}
