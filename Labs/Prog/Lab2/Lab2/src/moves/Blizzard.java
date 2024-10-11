package moves;

import ru.ifmo.se.pokemon.*;

public class Blizzard extends SpecialMove {
    public Blizzard(){
        super(Type.ICE, 110, 70);
    }
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        Effect effect = new Effect().condition(Status.FREEZE);
        if (Math.random() <= 0.1){
            pokemon.setCondition(effect);
        }
        pokemon.setCondition(new Effect().chance(0.1).condition(Status.FREEZE));
//
//        if (Math.random() <= 0.1){
//            Effect.freeze(pokemon);
//        }
    }

    @Override
    public String describe(){
        return "использует Blizzard";
    }
}
