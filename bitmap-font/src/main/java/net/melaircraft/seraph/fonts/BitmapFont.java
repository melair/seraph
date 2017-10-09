package net.melaircraft.seraph.fonts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BitmapFont {
    private final Map<Character, BitmapCharacter> characters = new HashMap<>();

    void addCharacter(char character, BitmapCharacter charObject) {
        characters.put(character, charObject);
    }

    public Optional<BitmapCharacter> getCharacter(char character) {
        return Optional.ofNullable(characters.get(character));
    }

    public FontOutput render(String text) {
        List<BitmapCharacter> characters = getCharactersForString(text);
        Set<BitmapPixel> pixels = new HashSet<>();

        int originX = 0;
        int originY = 0;

        for (BitmapCharacter character : characters) {
            int offsetX = character.getBitmapOffsetX();
            int offsetY = character.getBitmapOffsetY();

            boolean[][] bitmap = character.getBitmap();

            for (int x = 0; x < character.getBitmapWidth(); x++) {
                for (int y = 0; y < character.getBitmapHeight(); y++) {
                    pixels.add(new BitmapPixel(originX + offsetX + x, originY + offsetY + y, bitmap[x][y]));
                }
            }

            originX += character.getCharacterWidth();
            originY += character.getCharacterHeight();
        }

        IntSummaryStatistics xStats = pixels.stream().mapToInt(BitmapPixel::getX).summaryStatistics();
        IntSummaryStatistics yStats = pixels.stream().mapToInt(BitmapPixel::getY).summaryStatistics();

        int width = (xStats.getMax() + 1) - xStats.getMin();
        int height = (yStats.getMax() + 1) - yStats.getMin();

        int correctionX = xStats.getMin() * -1;
        int correctionY = yStats.getMin() * -1;

        FontOutput fontOutput = new FontOutput(width, height);

        pixels.stream().filter(BitmapPixel::isSet).forEach((pixel) -> {
            fontOutput.set(pixel.getX() + correctionX, pixel.getY() + correctionY);
        });

        return fontOutput;
    }

    private List<BitmapCharacter> getCharactersForString(String text) {
        return text.chars()
                .mapToObj(i -> (char)i)
                .map(this::getCharacter)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
