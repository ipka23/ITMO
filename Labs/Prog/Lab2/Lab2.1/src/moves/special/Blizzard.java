package moves.special;

import ru.ifmo.se.pokemon.*;

public class Blizzard extends SpecialMove {
    public Blizzard(){
        super(Type.ICE, 110, 70);
    }
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        Effect freeze = new Effect().chance(0.1).condition(Status.FREEZE);
        pokemon.addEffect(freeze);
    }

    @Override
    public String describe(){
        return "использует Blizzard";
    }
}
