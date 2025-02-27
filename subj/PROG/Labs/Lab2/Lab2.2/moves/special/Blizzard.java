package moves.special;

import ru.ifmo.se.pokemon.*;

public class Blizzard extends SpecialMove {
    private static final Type TYPE = Type.ICE;
    private static final double POWER = 110;
    private static final double ACCURACY = 70;

    public Blizzard() {
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
       Effect freeze = new Effect().chance(0.1).condition(Status.FREEZE);
       pokemon.addEffect(freeze);
    }
    @Override
    public String describe(){
        return "использует Blizzard";
    }
}
