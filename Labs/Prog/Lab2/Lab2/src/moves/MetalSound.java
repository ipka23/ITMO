package moves;

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
       for (int i = 0; i < 3; i++) {
           pokemon.setMod(Stat.SPECIAL_DEFENSE, -2);
       }
   }


   @Override
   public String describe(){
       return "использует Metal Sound";
   }
}
