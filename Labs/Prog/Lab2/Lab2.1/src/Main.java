import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Miltank p1 = new Miltank("Молоко", 40);
        Swablu p2 = new Swablu("Говорун", 12);
        Altaria p3 = new Altaria("Летун", 20);
        Honedge p4 = new Honedge("Спичка", 16);
        Doublade p5 = new Doublade("Нож", 16);
        AegislashBlade p6 = new AegislashBlade("Меч", 16);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}