import interfaces.CanStandUp;

public class Character {
    private String name;
    public Character() {
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return getName();
    }
    public void standUp() {
        System.out.println(name + "поднялся");
    }
}
