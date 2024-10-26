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
            Effect evasion = new Effect().stat(Stat.EVASION, 1);
            pokemon.addEffect(evasion);
            evasionModificator += 1;
//            System.out.println(evasionModificator);
        }
        else {
            Effect evasion = new Effect().chance(0).stat(Stat.EVASION, 0);
            pokemon.addEffect(evasion);
        }
    }
    @Override
    protected String describe(){
        return "использует Double Team";
    }
}
