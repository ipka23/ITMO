package moves.special;

import ru.ifmo.se.pokemon.*;

public class DragonBreath extends SpecialMove {
    public DragonBreath() {
        super(Type.DRAGON, 60, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon pokemon){
        Effect paralyze = new Effect().chance(0.3).condition(Status.PARALYZE);
        pokemon.setCondition(paralyze);
    }
    @Override
    public String describe(){
        return "использует Dragon Breath";
    }
}
