package moves.status;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class MetalSound extends StatusMove {
   public MetalSound(){
       super(Type.STEEL, 0, 85);
   }

   private int count = 0;
   @Override
   protected void applyOppEffects(Pokemon pokemon){
       pokemon.setMod(Stat.SPECIAL_DEFENSE, -2);
       count++;
       if (count >= 3){
           pokemon.setMod(Stat.SPECIAL_DEFENSE, 0);
       }
   }


   @Override
   public String describe(){
       return "использует Metal Sound";
   }
}
