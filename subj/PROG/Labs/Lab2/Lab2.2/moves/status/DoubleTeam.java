package moves.status;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    private static final Type TYPE = Type.NORMAL;
    public DoubleTeam(){
        super(TYPE, 0, 0);
    }
    private int evasionModificator = 0;
    @Override
    protected void applySelfEffects(Pokemon pokemon){
        if (evasionModificator <= 6) {
            pokemon.setMod(Stat.EVASION, 0);
            evasionModificator += 1;
//            System.out.println(evasionModificator);
        }
        else {
            pokemon.setMod(Stat.EVASION, 0);
        }
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }
    @Override
    protected String describe(){
        return "использует Double Team";
    }
}
