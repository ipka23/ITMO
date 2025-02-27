package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.Game;
import ru.ipka23.newjavalab3.interfaces.Playable;
import ru.ipka23.newjavalab3.interfaces.StoryTeller;

import java.util.HashMap;

public class GameAction implements Playable, StoryTeller {
    private Game game;
    private AbstractCharacter character;
    private final HashMap<Game, Boolean> gameStatus = new HashMap<>();

    @Override
    public void play(AbstractCharacter character, Game game) {
        this.character = character;
        this.game = game;
        gameStatus.put(game, true);
    }

    @Override
    public void dontPlay(AbstractCharacter character, Game game) {
        this.character = character;
        this.game = game;
        gameStatus.put(game, false);
    }

    @Override
    public void tell() {
        // пробегаемся по всем парам в Hash Map и сохраняем значения игры и статуса в соответствующие переменные
        for (HashMap.Entry<Game, Boolean> entry : gameStatus.entrySet()) {
            Game currentGame = entry.getKey();
            boolean isPlaying = entry.getValue();

            if (isPlaying) {
                System.out.println(character.getName() + " играет в " + currentGame.getGame());
            } else {
                System.out.println(character.getName() + " не играет в " + currentGame.getGame());
            }
        }
    }
}