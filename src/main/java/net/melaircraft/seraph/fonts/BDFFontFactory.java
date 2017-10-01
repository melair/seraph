package net.melaircraft.seraph.fonts;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BDFFontFactory {
    private BDFFontFactory() {}

    public static BitmapFont load(InputStream inputStream) throws IOException {
        BitmapFont font = new BitmapFont();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        int fontWidth = 8;
        int fontHeight = 8;
        int fontOffsetX = 0;
        int fontOffsetY = 0;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");

            switch (parts[0]) {
                case "FONTBOUNDINGBOX":
                    fontWidth = Integer.parseInt(parts[1]);
                    fontHeight = Integer.parseInt(parts[2]);
                    fontOffsetX = Integer.parseInt(parts[3]);
                    fontOffsetY = Integer.parseInt(parts[4]);
                    break;

                case "STARTCHAR":
                    loadCharacter(font, reader, fontWidth, fontHeight, fontOffsetX, fontOffsetY);
                    break;
            }
        }

        return font;
    }

    private static void loadCharacter(BitmapFont font, BufferedReader reader, int fontWidth, int fontHeight, int fontOffsetX, int fontOffsetY) throws IOException {
        String line;
        Character character = null;
        boolean bitmap[][] = null;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");

            switch (parts[0]) {
                case "ENCODING":
                    char converted[] = Character.toChars(Integer.parseInt(parts[1]));
                    character = converted[0];
                    break;

                case "BBX":
                    fontWidth = Integer.parseInt(parts[1]);
                    fontHeight = Integer.parseInt(parts[2]);
                    fontOffsetX = Integer.parseInt(parts[3]);
                    fontOffsetY = Integer.parseInt(parts[4]);
                    break;

                case "BITMAP":
                    bitmap = new boolean[fontWidth][fontHeight];

                    for (int y = 0; y < fontHeight; y++) {
                        byte[] data = DatatypeConverter.parseHexBinary(reader.readLine());

                        int x = 0;

                        for (int b = 0; b < data.length; b++) {
                            for (int p = 7; p >= 0; p--) {
                                boolean value = ((data[b] >> p) & 0x01) != 0;

                                if (x < fontWidth) {
                                    bitmap[x][y] = value;
                                }

                                x++;
                            }
                        }
                    }
                    break;

                case "ENDCHAR":
                    if (character != null) {
                        font.addCharacter(character, new BitmapCharacter(bitmap, fontWidth, fontHeight, fontOffsetX, fontOffsetY));
                    }
                    return;
            }
        }
    }
}
