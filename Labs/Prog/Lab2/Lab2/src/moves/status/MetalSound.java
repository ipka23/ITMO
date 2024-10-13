package moves.status;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class MetalSound extends StatusMove {
   public MetalSound(){
       super(Type.STEEL, 0, 85);
   }


   @Override
   protected void applyOppEffects(Pokemon pokemon){
       for (int i = -2; i > -7; i-=2) {
           pokemon.setMod(Stat.SPECIAL_DEFENSE, i);
       }
   }


   @Override
   public String describe(){
       return "использует Metal Sound";
   }
}
