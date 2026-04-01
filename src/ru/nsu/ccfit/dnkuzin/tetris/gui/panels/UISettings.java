package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import ru.nsu.ccfit.dnkuzin.tetris.audio.SoundEffect;
import ru.nsu.ccfit.dnkuzin.tetris.audio.SoundPlayer;
import ru.nsu.ccfit.dnkuzin.tetris.gui.Fonts;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class UISettings {
    private static final Color BTN_BORDER = new Color(210, 55, 55, 180);
    private static final Color BTN_HOVER_BORDER = new Color(235, 85, 85);
    private static final Color BTN_HOVER = new Color(190, 50, 50, 180);
    private static final Color BTN_TEXT = new Color(245, 245, 250);

    private static final Color BTN_PRESS = new Color(150, 35, 35, 200);


    public static void styleButton(JButton button) {
        button.addActionListener(e -> {
            SoundPlayer.play(SoundEffect.BUTTON);
        });
        button.setFont(Fonts.getTextFont(20f));
        button.setForeground(BTN_TEXT);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setRolloverEnabled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                Graphics2D g2 = (Graphics2D) g.create();

                if (model.isPressed()) {
                    g2.setColor(BTN_PRESS);
                    g2.fillRect(0, 0, b.getWidth(), b.getHeight());
                } else if (model.isRollover()) {
                    g2.setColor(BTN_HOVER);
                    g2.fillRect(0, 0, b.getWidth(), b.getHeight());
                }

                g2.dispose();
                super.paint(g, c);
            }
        });
    }
}