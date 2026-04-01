package ru.nsu.ccfit.dnkuzin.tetris.gui.panels;

import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.gui.Fonts;
import ru.nsu.ccfit.dnkuzin.tetris.model.GameState;
import ru.nsu.ccfit.dnkuzin.tetris.score.HighScores;
import ru.nsu.ccfit.dnkuzin.tetris.score.HighScoresEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScorePanel extends SpaceBackgroundPanel {
    private JTable table;
    private JScrollPane scrollPane;
    public void updateTable(){
        HighScores highScores = new HighScores();
        List<HighScoresEntry> entries = highScores.getEntries();
        String[] columns = {"ИМЯ", "СЧЁТ"};
        int countOfRecords = Math.min(HighScores.getMaxRecord(), entries.size());
        String[][] data = new String[countOfRecords][2];


        for (int i = 0; i < countOfRecords; i++){
            data[i][0] = entries.get(i).name();
            data[i][1] = String.valueOf(entries.get(i).score());
        }
        table = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(32);
        table.setFont(new Font("Consolas", Font.BOLD, 16));
        table.setBackground(new Color(18, 18, 22));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(40, 40, 60));
        table.setShowGrid(false);


        if (scrollPane != null) {
            scrollPane.setViewportView(table);
        }

    }
    public HighScorePanel(GameController controller) {
        setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        JLabel title = new JLabel("Топ-" + HighScores.getMaxRecord(), SwingConstants.CENTER);
        title.setFont(Fonts.getTitleFont(30f));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateTable();

        JButton menuButton = new JButton("Назад");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        UISettings.styleButton(menuButton);
        menuButton.addActionListener(_ -> controller.setGameState(GameState.NOT_STARTED));

        scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBorder(null);
        scrollPane.setViewportBorder(null);

        content.add(title);
        content.add(Box.createVerticalStrut(40));
        content.add(scrollPane);
        content.add(Box.createVerticalStrut(40));
        content.add(menuButton);
        add(content);


        setFocusable(true);
    }
}
