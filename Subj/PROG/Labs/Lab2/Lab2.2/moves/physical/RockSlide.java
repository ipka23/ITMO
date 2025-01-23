package moves.physical;

import ru.ifmo.se.pokemon.*;

public class RockSlide extends PhysicalMove {
    private static final Type TYPE = Type.ROCK;
    private static final double POWER = 75;
    private static final double ACCURACY = 90;
    public RockSlide(){
        super(TYPE, POWER, ACCURACY);
    }
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        if (Math.random() <= 0.3){
            Effect.flinch(pokemon);
        }
    }
    @Override
    public String describe(){
        return "использует Rock Slide";
    }
}
