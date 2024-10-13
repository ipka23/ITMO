package moves.physical;

import ru.ifmo.se.pokemon.*;
import ru.ifmo.se.pokemon.Effect;


public class WakeUpSlap extends PhysicalMove {
    public WakeUpSlap() {
        super(Type.FIGHTING, 70, 100);  // type, power, accuracy
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect normalCondition = new Effect().condition(Status.NORMAL);
        if (pokemon.getCondition() == Status.SLEEP){
            pokemon.setMod(Stat.ATTACK, 2);
            pokemon.setCondition(normalCondition);
        }
    }
    @Override
    protected String describe() {
        return "использует Wake-Up Slap";
    }
}