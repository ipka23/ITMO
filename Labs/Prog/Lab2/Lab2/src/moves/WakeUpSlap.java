package moves;

import ru.ifmo.se.pokemon.*;
import ru.ifmo.se.pokemon.Effect;


public class WakeUpSlap extends PhysicalMove {
    public WakeUpSlap() {
        super(Type.FIGHTING, 70, 100);  // type, power, accuracy
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Status pokemonCondition = pokemon.getCondition();
        if (pokemonCondition == Status.SLEEP){
            pokemon.setMod(Stat.ATTACK, 2);
        }
    }
    @Override
    protected String describe() {
        return "использует Wake-Up Slap";
    }
}
