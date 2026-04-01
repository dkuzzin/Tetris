package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.gui.Fonts;
import ru.nsu.ccfit.dnkuzin.tetris.model.GameState;
import ru.nsu.ccfit.dnkuzin.tetris.score.HighScores;

import javax.swing.*;
import java.awt.*;


public class EnterNamePanel extends SpaceBackgroundPanel{
    private final JTextField nameField;
    private final GameController controller;
    public EnterNamePanel(GameController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 18, 22));

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(4, 1, 0, 10));
        content.setOpaque(false);

        JLabel title = new JLabel("Введите свое имя", SwingConstants.CENTER);
        title.setFont(Fonts.getTitleFont(20f));
        title.setForeground(Color.WHITE);

        nameField = new JTextField();
        JButton saveButton = new JButton("Сохранить");
        UISettings.styleButton(saveButton);
        saveButton.addActionListener(_->saveRecord());


        JButton exitButton = new JButton("Отмена");
        exitButton.addActionListener(_ -> controller.setGameState(GameState.GAME_OVER));

        UISettings.styleButton(exitButton);

        content.add(title);
        content.add(nameField);
        content.add(saveButton);
        content.add(exitButton);
        add(content);

        setFocusable(true);
    }
    private void saveRecord(){
        String name = nameField.getText().trim();
        if (name.isBlank()){
            JOptionPane.showMessageDialog(
                    this,
                    "Имя не должно быть пустым, попробуйте еще раз",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (HighScores.isNameAlreadyExist(name)){
            JOptionPane.showMessageDialog(
                    this,
                    "Рекорд с таким именем уже записан, попробуйте еще раз",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (name.length() > 15){
            name = name.substring(0, 15);
        }
        HighScores recordTable = controller.getRecordTable();
        if (recordTable == null){
            JOptionPane.showMessageDialog(
                    this,
                    "[SYS ERROR] Таблица рекорда сейчас недоступна",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        recordTable.add(name, controller.getSavedScore());
        controller.setIsRecord(false);
        controller.setGameState(GameState.GAME_OVER);
    }
}
