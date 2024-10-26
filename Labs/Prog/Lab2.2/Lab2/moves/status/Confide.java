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
            Effect specialDefenceReduction = new Effect().stat(Stat.SPECIAL_DEFENSE, -1);
            specialDefenseModificator += 1;
            pokemon.addEffect(specialDefenceReduction);
//            System.out.println(pokemon + "понижает специальную защиту вражеского покемона на 1 ступень!");
        }
        else {
            Effect specialDefense = new Effect().chance(0).stat(Stat.SPECIAL_DEFENSE, 0);
            pokemon.addEffect(specialDefense);
        }
    }
    @Override
    protected String describe(){
        return "использует Confide";
    }
}
