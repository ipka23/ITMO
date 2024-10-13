package moves.physical;

import ru.ifmo.se.pokemon.*;

public class HeadSmash extends PhysicalMove {
    public HeadSmash(){
        super(Type.ROCK, 150, 80);
    }


    @Override
    protected void applySelfDamage(Pokemon pokemon, double damage){
        pokemon.setMod(Stat.HP, (int) (0.5 * Math.round(damage)));
    }
    @Override
    public String describe(){
        return "использует Head Smash";
    }
}
