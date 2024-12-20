package ru.ipka23.newjavalab3.interfaces;

import ru.ipka23.newjavalab3.characters.AbstractCharacter;
import ru.ipka23.newjavalab3.enums.Game;


public interface Playable {
    void play(AbstractCharacter character, Game game);
    void dontPlay(AbstractCharacter character, Game game);
}
