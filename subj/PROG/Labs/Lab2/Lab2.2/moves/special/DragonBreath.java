package moves.special;


import ru.ifmo.se.pokemon.*;

public class DragonBreath extends SpecialMove {
    private static final Type TYPE = Type.DRAGON;
    private static final double POWER = 60;
    private static final double ACCURACY = 100;
    public DragonBreath(){
        super(TYPE, POWER, ACCURACY);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon){
        Effect paralyze = new Effect().chance(0.3).condition(Status.PARALYZE);
        pokemon.addEffect(paralyze);
    }
    @Override
    public String describe(){
        return "использует Dragon Breath";
    }
}
