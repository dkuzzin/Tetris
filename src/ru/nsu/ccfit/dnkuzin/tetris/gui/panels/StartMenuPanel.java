package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;
import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.model.GameState;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;


import static java.lang.System.exit;

public class StartMenuPanel extends SpaceBackgroundPanel{
    public StartMenuPanel(GameController controller){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;


        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        try{
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo.png")));
            JLabel title = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(260, 174,
                    Image.SCALE_SMOOTH)));

            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            content.add(Box.createVerticalStrut(100));
            content.add(title);
        }catch (NullPointerException e){
            System.err.println("[ERROR StartMenuPanel] Cannot load the Tetris logo: " + e.getMessage());
        }


        JButton startButton = new JButton("Новая игра");
        startButton.addActionListener(_-> controller.setGameState(GameState.RUNNING));
        UISettings.styleButton(startButton);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton scoreButton = new JButton("Рекорды");
        scoreButton.addActionListener(_-> controller.setGameState(GameState.SCOREBOARD));
        UISettings.styleButton(scoreButton);
        scoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener(_->exit(0));
        UISettings.styleButton(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton githubButton = new JButton("Автор");
        githubButton.addActionListener(_->gitLink());
        UISettings.styleButton(githubButton);
        githubButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(100));
        content.add(startButton);
        content.add(Box.createVerticalStrut(15));
        content.add(scoreButton);
        content.add(Box.createVerticalStrut(15));
        content.add(exitButton);
        content.add(Box.createVerticalStrut(15));
        content.add(githubButton);
        add(content, gbc);
    }

    public void gitLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/dkuzzin"));
        } catch (IOException | URISyntaxException ex) {
            System.err.println("Cannot open link");
        }
    }
}
