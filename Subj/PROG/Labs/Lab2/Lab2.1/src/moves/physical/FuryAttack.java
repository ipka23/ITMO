package moves.physical;

import ru.ifmo.se.pokemon.*;

public class FuryAttack extends PhysicalMove {
    public FuryAttack() {
        super(Type.NORMAL, 15, 85);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon){
        Effect powerX = new Effect().chance((double) 3/8).stat(Stat.ATTACK, 2).chance((double) 3/8).stat(Stat.ATTACK, 3).chance((double) 1/8).stat(Stat.ATTACK, 4).chance((double) 1/8).stat(Stat.ATTACK, 5);
        pokemon.addEffect(powerX);
    }

    @Override
    public String describe(){
        return "использует Fury Attack";
    }
}
