import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Miltank p1 = new Miltank("Мазурова", 10);
        Swablu p2 = new Swablu("Биба", 10);
        Altaria p3 = new Altaria("Боба", 10);
        Honedge p4 = new Honedge("Ножик", 10);
        Doublade p5 = new Doublade("Нож", 10);
        AegislashBlade p6 = new AegislashBlade("Ножище", 10);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();


    }
}