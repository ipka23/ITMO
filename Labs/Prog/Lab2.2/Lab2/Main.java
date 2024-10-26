import moves.physical.BrutalSwing;
import moves.physical.HeadSmash;
import moves.special.DreamEater;
import moves.status.DoubleTeam;
import pokemons.AegislashBlade;
import pokemons.Honedge;
import pokemons.Swablu;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
//         if (p1.hasType(Type.STEEL)){
//             System.out.println("YES");
//         }
//         else {
//             System.out.println("NO");
//         }
        Battle b = new Battle();
        Effect sleep = new Effect().condition(Status.SLEEP);
        Pokemon p2 = new Honedge("pokemon", 1);
        Pokemon p1 = new Swablu("pokemon", 1);
//        p1.addEffect(sleep);
        b.addAlly(p1);
        b.addFoe(p2);
        b.go();
    }
}