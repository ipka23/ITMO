public class Neznayka extends Character implements Playable {
    private String name;
    public Neznayka(String name){
        super(name);
    }
    public String getName(){
        return name;
    }
    @Override
    public void play() {
        // Создаем объект Game (например, TOWNS) и используем его для игры
        Game game = Game.GAME; // Здесь можно передать нужную игру
        System.out.println(getName() + " играет в " + getName());
    }

    public String play(Game game) {
        return getName() + " игрaть в " + getName();
    }
}