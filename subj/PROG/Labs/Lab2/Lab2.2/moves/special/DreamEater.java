package moves.special;

import moves.status.MetalSound;
import ru.ifmo.se.pokemon.*;

public class DreamEater extends SpecialMove {
    private static final Type TYPE = Type.PSYCHIC;
    private static final double POWER = 100;
    private static final double ACCURACY = 100;
    public DreamEater(){
        super(TYPE, POWER, ACCURACY);
    }


    @Override
    protected void applyOppDamage(Pokemon pokemon, double damage){
        if (pokemon.getCondition() == Status.SLEEP){
            pokemon.setMod(Stat.HP, (int) Math.round(damage));
        }
        else {
            pokemon.setMod(Stat.HP, 0);
        }
    }
    @Override
    protected void applySelfDamage(Pokemon pokemon, double damage){
        if (pokemon.getCondition() == Status.SLEEP){
            pokemon.setMod(Stat.HP, -(int) Math.round(0.5*damage));
        }
        else {
            pokemon.setMod(Stat.HP, 0);
        }
    }
    @Override
    protected String describe(){
        return "использует Dream Eater";
    }
}
