package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.gui.Fonts;
import ru.nsu.ccfit.dnkuzin.tetris.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class GameOverPanel extends SpaceBackgroundPanel{
    private final GameController controller;
    private final JLabel score;
    private final JButton saveRecordButton;
    public void updateScore() {
        score.setText("ИТОГ: " + controller.getSavedScore());
        saveRecordButton.setVisible(controller.getIsRecord());
    }
    public GameOverPanel(GameController controller){
        this.controller = controller;
        setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/die.png")));
        Image img = icon.getImage().getScaledInstance(260, 174, Image.SCALE_SMOOTH);

        JLabel title = new JLabel(new ImageIcon(img));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        score = new JLabel("Score: " + controller.getSavedScore());
        score.setFont(Fonts.getTextFont(25f));
        score.setForeground(Color.WHITE);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveRecordButton = new JButton("Сохранить рекорд");
        UISettings.styleButton(saveRecordButton);
        saveRecordButton.addActionListener(_ -> controller.setGameState(GameState.SAVE_RECORD));
        saveRecordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //saveRecordButton.setVisible(false);

        JButton startButton = new JButton("Заново");
        UISettings.styleButton(startButton);
        startButton.addActionListener(_ -> controller.setGameState(GameState.RUNNING));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Меню");
        UISettings.styleButton(exitButton);
        exitButton.addActionListener(_ -> controller.setGameState(GameState.NOT_STARTED));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        content.add(title);
        content.add(score);
        content.add(Box.createVerticalStrut(15));
        content.add(saveRecordButton);
        content.add(Box.createVerticalStrut(15));
        content.add(startButton);
        content.add(Box.createVerticalStrut(15));
        content.add(exitButton);
        add(content);
    }
}
