package ru.ipka23.newjavalab3.actions;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.Game;
import ru.ipka23.newjavalab3.interfaces.Playable;

public class GameAction implements Playable {
    private boolean isPlaying;


    @Override
    public void play(AbstractCharacter character, Game game, boolean isPlaying) {
        if (isPlaying) {
            this.isPlaying = true;
            System.out.println(character.getName() + " играет в " + game.getGame());
        }
        else {
            this.isPlaying = false;
            System.out.println(character.getName() + " не играет в " + game.getGame());
        }
    }


    @Override
    public boolean getPlayingStatus(AbstractCharacter character) {
        return isPlaying;
    }
}