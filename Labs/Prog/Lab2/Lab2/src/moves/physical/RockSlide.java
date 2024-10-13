package moves.physical;

import ru.ifmo.se.pokemon.*;

public class RockSlide extends PhysicalMove {
    public RockSlide(){
        super(Type.ROCK, 75, 90);
    }

    private boolean isFlinched = false;
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        if ((Math.random() < 0.3) & (isFlinched)){
            Effect.flinch(pokemon);
            isFlinched = true;
        }
    }

    @Override
    protected String describe(){
        return "использует Rock Slide";
    }
}
