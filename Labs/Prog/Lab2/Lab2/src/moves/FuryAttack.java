package moves;

import ru.ifmo.se.pokemon.*;

public class FuryAttack extends PhysicalMove {
    public FuryAttack(){
        super(Type.NORMAL, 15, 85);
    }


    @Override
    protected void applyOppDamage(Pokemon defendingPokemon, double damage){
        defendingPokemon.setCondition(new Effect().chance((double) 3/8).stat(Stat.ATTACK, 2).);// прописать power
        defendingPokemon.setCondition(new Effect().chance((double) 3/8).stat(Stat.ATTACK, 2));//
        defendingPokemon.setCondition(new Effect().chance((double) 1/8).stat(Stat.ATTACK, 4));//
        defendingPokemon.setCondition(new Effect().chance((double) 1/8).stat(Stat.ATTACK, 5));//
    }
}
