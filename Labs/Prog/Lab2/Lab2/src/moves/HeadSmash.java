package moves;

import ru.ifmo.se.pokemon.*;

public class HeadSmash extends PhysicalMove {
    public HeadSmash(){
        super(Type.ROCK, 150, 80);
    }


    @Override
    protected void applyOppDamage(Pokemon defendingPokemon, double damage){
        defendingPokemon.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected void applySelfDamage(Pokemon attackingPokemon, double damage){
        attackingPokemon.setMod(Stat.HP, ((int) (0.5 * (Math.round(damage)))));
    }
    @Override
    public String describe(){
        return "использует Head Smash";
    }
}
