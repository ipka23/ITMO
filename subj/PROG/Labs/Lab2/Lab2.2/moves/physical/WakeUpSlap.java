package moves.physical;

import ru.ifmo.se.pokemon.*;

import java.util.EventListener;

public class WakeUpSlap extends PhysicalMove {
    private static final Type TYPE = Type.FIGHTING;
    private static final double POWER = 70;
    private static final double ACCURACY = 100;
    public WakeUpSlap(){
        super(TYPE, POWER, ACCURACY);
    }
    @Override
    protected void applyOppDamage(Pokemon pokemon, double damage){
        if (pokemon.getCondition() == Status.SLEEP){
            pokemon.setMod(Stat.HP, (int) Math.round(damage*2));
            Effect normalCondition = new Effect().condition(Status.NORMAL);
            pokemon.addEffect(normalCondition);
        }
    }
    @Override
    public String describe(){
        return "использует Wake-Up Slap";
    }
}
