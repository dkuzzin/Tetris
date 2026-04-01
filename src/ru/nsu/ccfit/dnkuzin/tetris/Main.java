package ru.nsu.ccfit.dnkuzin.tetris;

import ru.nsu.ccfit.dnkuzin.tetris.controller.GameController;
import ru.nsu.ccfit.dnkuzin.tetris.gui.*;
import ru.nsu.ccfit.dnkuzin.tetris.gui.panels.*;
import ru.nsu.ccfit.dnkuzin.tetris.model.GameModel;
import ru.nsu.ccfit.dnkuzin.tetris.model.GameState;


public class Main {
    private static void initFrame(GameModel model, GameController controller){
        StartMenuPanel startPanel = new StartMenuPanel(controller);
        PausePanel pausePanel = new PausePanel(controller);
        GamePanel gamePanel = new GamePanel(model, controller);
        GameOverPanel gameOverPanel = new GameOverPanel(controller);
        HighScorePanel scorePanel = new HighScorePanel(controller);
        EnterNamePanel enterPanel = new EnterNamePanel(controller);
        GameFrame frame = new GameFrame(pausePanel, gamePanel, startPanel, gameOverPanel, scorePanel, enterPanel);
        controller.setGamePanel(gamePanel);
        controller.setGameFrame(frame);
    }

    public static void main(String[] args){
        GameModel model = new GameModel();
        GameController controller = new GameController(model);
        initFrame(model, controller);
        controller.setGameState(GameState.NOT_STARTED);
    }
}
