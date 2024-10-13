package moves.special;

import ru.ifmo.se.pokemon.*;

public class DreamEater extends SpecialMove {
    public DreamEater(){
        super(Type.PSYCHIC, 100, 100);
    }

    @Override
    protected void applySelfDamage(Pokemon attackingPokemon, double damage){
        attackingPokemon.setMod(Stat.HP, -((int) Math.round(damage)));
    }
    @Override
    protected void applyOppDamage(Pokemon defendingPokemon, double damage){
        if (defendingPokemon.getCondition() == Status.SLEEP){
            defendingPokemon.setMod(Stat.HP, (int) Math.round(damage));
        }
    }
    @Override
    public String describe(){
        return "использует Dream Eater";
    }
}