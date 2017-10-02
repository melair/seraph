package net.melaircraft.seraph.fonts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BitmapFont {
    private final Map<Character, BitmapCharacter> characters = new HashMap<>();

    public void addCharacter(char character, BitmapCharacter charObject) {
        characters.put(character, charObject);
    }

    public Optional<BitmapCharacter> getCharacter(char character) {
        return Optional.ofNullable(characters.get(character));
    }
}
