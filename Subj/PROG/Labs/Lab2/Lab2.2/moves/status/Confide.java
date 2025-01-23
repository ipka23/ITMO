package moves.status;

import ru.ifmo.se.pokemon.*;


public class Confide extends StatusMove {
    private static final Type TYPE = Type.NORMAL;
    public Confide(){
        super(TYPE, 0, 0);
    }
    private int specialDefenseModificator = 0;
    @Override
    protected void 	applyOppEffects(Pokemon pokemon){
        if (specialDefenseModificator < 6){
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -1);
            specialDefenseModificator++;
        }
        else {
            pokemon.setMod(Stat.SPECIAL_DEFENSE, 0);
        }
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }
    @Override
    protected String describe(){
        return "использует Confide";
    }
}
