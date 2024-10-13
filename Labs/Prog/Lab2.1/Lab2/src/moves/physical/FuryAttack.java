package moves.physical;

import ru.ifmo.se.pokemon.*;

public class FuryAttack extends PhysicalMove {
    public FuryAttack() {
        super(Type.NORMAL, 15, 85);
//        if (Math.random() <= 0.375) {
//            this.hits = 2;
//        }
//        else if ((0.375 <= Math.random()) & (Math.random() <= 0.75)) {
//            this.hits = 3;
//        }
//        else if ((0.75 <= Math.random()) & (Math.random() <= 0.875)) {
//            this.hits = 4;
//        }
//        else if (0.875 <= Math.random()) {
//            this.hits = 5;
//        }
    }
    @Override
    protected void applySelfEffects(Pokemon pokemon){
        Effect powerX2 = new Effect().chance((double) 3/8).stat(Stat.ATTACK, 2);
        Effect powerX3 = new Effect().chance((double) 3/8).stat(Stat.ATTACK, 3);
        Effect powerX4 = new Effect().chance((double) 1/8).stat(Stat.ATTACK, 4);
        Effect powerX5 = new Effect().chance((double) 1/8).stat(Stat.ATTACK, 5);
        pokemon.addEffect(powerX2);
        pokemon.addEffect(powerX3);
        pokemon.addEffect(powerX4);
        pokemon.addEffect(powerX5);
    }
    @Override
    public String describe(){
        return "использует Fury Attack";
    }
}
