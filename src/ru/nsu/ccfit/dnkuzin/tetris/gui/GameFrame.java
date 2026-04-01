package ru.nsu.ccfit.dnkuzin.tetris.gui;
import ru.nsu.ccfit.dnkuzin.tetris.gui.panels.*;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
        private final GamePanel gamePanel;
        private final PausePanel pausePanel;
        private final GameOverPanel gameOverPanel;
        private final StartMenuPanel startPanel;
        private final JPanel rootPanel;
        private final EnterNamePanel enterNamePanel;
        private final HighScorePanel scorePanel;
        private final CardLayout cardLayout;


        public GameFrame(PausePanel pausePanel, GamePanel gamePanel,
                         StartMenuPanel startPanel, GameOverPanel gameOverPanel,
                         HighScorePanel scorePanel, EnterNamePanel enterPanel){
            this.pausePanel = pausePanel;
            this.gamePanel = gamePanel;
            this.startPanel = startPanel;
            this.gameOverPanel = gameOverPanel;
            this.scorePanel = scorePanel;
            this.enterNamePanel = enterPanel;

            Image icon = Toolkit.getDefaultToolkit()
                    .getImage(getClass().getResource("/img/icon2.png"));

            setIconImage(icon);

            setTitle("Tetris");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            cardLayout = new CardLayout();
            rootPanel = new JPanel(cardLayout);

            rootPanel.add(this.startPanel, "START");
            rootPanel.add(this.pausePanel, "MENU");
            rootPanel.add(this.gamePanel, "GAME");
            rootPanel.add(this.gameOverPanel, "GAME OVER");
            rootPanel.add(scorePanel, "SCORE");
            rootPanel.add(enterPanel, "SAVE_SCORE");

            add(rootPanel);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);
        }


        public void showMenu(){
            cardLayout.show(rootPanel, "MENU");
            pausePanel.requestFocusInWindow();
        }
        public void showStart(){
            cardLayout.show(rootPanel, "START");
            startPanel.requestFocusInWindow();
        }

        public void showGame(){
            cardLayout.show(rootPanel, "GAME");
            gamePanel.requestFocusInWindow();
        }
        public void showGameOver(){
            gameOverPanel.updateScore();
            cardLayout.show(rootPanel, "GAME OVER");
            gameOverPanel.requestFocusInWindow();
        }
        public void showScore(){
            scorePanel.updateTable();
            cardLayout.show(rootPanel, "SCORE");
            scorePanel.requestFocusInWindow();
        }
        public void showSaveScore(){
            cardLayout.show(rootPanel, "SAVE_SCORE");
            enterNamePanel.requestFocusInWindow();
        }
}
