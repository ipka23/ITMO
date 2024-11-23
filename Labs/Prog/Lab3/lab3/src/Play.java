public class Play extends Action{
    private final Game game;

    public Play(Neznayka neznayka, Game game) {
        this.game = game;
    }
    @Override
    public String doSomething() {
        return "играть " + game;
    }
}