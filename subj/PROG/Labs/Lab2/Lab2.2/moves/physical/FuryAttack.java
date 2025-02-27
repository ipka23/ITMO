package moves.physical;

import ru.ifmo.se.pokemon.*;

public class FuryAttack extends PhysicalMove {
    private static final Type TYPE = Type.NORMAL;
    private static final double POWER = 15;
    private static final double ACCURACY = 85;
    public FuryAttack(int hits) {
        super(TYPE, POWER, ACCURACY, 0, hits);
    }
    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
        if (att.getStat(Stat.SPEED) / 512.0 > Math.random()) {
            System.out.println(att + " наносит критический удар по " + def);
            return 2.0;
        } else {
            return 1.0;
        }
    }
    @Override
    protected String describe(){
        return "использует Fury Attack";
    }
}