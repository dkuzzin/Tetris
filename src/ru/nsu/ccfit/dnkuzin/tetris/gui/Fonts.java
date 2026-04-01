package ru.nsu.ccfit.dnkuzin.tetris.gui;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Fonts {
    private static final Font baseFont;
    private static final Font textFont;
    static{
        try{
            baseFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(Fonts.class.getResourceAsStream("/fonts/vcrosdmonorusbyd.ttf"))
            );
            textFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(Fonts.class.getResourceAsStream("/fonts/EpilepsySansBold.ttf"))
            );
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
    public static Font getTitleFont(float size) {
        return baseFont.deriveFont(Font.PLAIN, size);
    }
    public static Font getTextFont(float size) {
        return textFont.deriveFont(Font.PLAIN, size);
    }
}
