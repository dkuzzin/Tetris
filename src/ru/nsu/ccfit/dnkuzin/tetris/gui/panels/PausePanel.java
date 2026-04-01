package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.gui.Fonts;
import ru.nsu.ccfit.dnkuzin.tetris.model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PausePanel extends SpaceBackgroundPanel{
    public PausePanel(GameController controller) {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 18, 22));

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(3, 1, 0, 10));
        content.setOpaque(false);

        JLabel title = new JLabel("ПАУЗА", SwingConstants.CENTER);
        title.setFont(Fonts.getTitleFont(30f));
        title.setForeground(Color.WHITE);


        JButton startButton = new JButton("Продолжить");
        UISettings.styleButton(startButton);
        startButton.addActionListener(_ -> controller.setGameState(GameState.RUNNING));

        JButton exitButton = new JButton("Меню");
        exitButton.addActionListener(_ -> controller.setGameState(GameState.NOT_STARTED));

        UISettings.styleButton(exitButton);

        content.add(title);
        content.add(startButton);
        content.add(exitButton);
        add(content);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyPressed(e.getKeyCode());
            }
        });
    }
}
