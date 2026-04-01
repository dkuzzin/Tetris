package ru.nsu.ccfit.dnkuzin.tetris.controller;
import ru.nsu.ccfit.dnkuzin.tetris.audio.MusicPlayer;
import ru.nsu.ccfit.dnkuzin.tetris.exceptions.*;
import ru.nsu.ccfit.dnkuzin.tetris.gui.*;
import ru.nsu.ccfit.dnkuzin.tetris.gui.panels.*;
import ru.nsu.ccfit.dnkuzin.tetris.model.*;
import ru.nsu.ccfit.dnkuzin.tetris.score.HighScores;

import java.awt.event.KeyEvent;
import javax.swing.*;


public class GameController {
    private final GameModel gameModel;
    private final Timer timer;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private final HighScores recordTable;
    private boolean isRecord = false;
    private int savedScore = 0;
    final int STANDARD_SPEED = 1000;
    private final Timer moveTimer;
    private Direction heldDirection = null;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    public GameController(GameModel model){
        this.gameModel = model;
        timer = new Timer(STANDARD_SPEED, _ -> tick());
        moveTimer = new Timer(40, _-> {
            if (gameModel.getState() != GameState.RUNNING){
                return;
            }
            if (heldDirection != null){
                gameModel.move(heldDirection);
                if (gamePanel != null){
                    gamePanel.repaint();
                }
            }
        });
        moveTimer.setInitialDelay(170);
        setGameState(GameState.NOT_STARTED);
        recordTable = new HighScores();
    }

    public GameModel getModel(){
        return gameModel;
    }
    public HighScores getRecordTable(){
        return recordTable;
    }
    public int getSavedScore(){return savedScore;}
    public boolean getIsRecord(){return isRecord;}
    public void setIsRecord(boolean isRecord){
        this.isRecord = isRecord;
    }
    public void setGameFrame(GameFrame gameFrame){
        this.gameFrame = gameFrame;
    }
    public void setGamePanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    private void showErrorToUser(String message) {
        if (gameFrame != null) {
            JOptionPane.showMessageDialog(gameFrame, message,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        System.err.println(message);
    }

    public void setGameState(GameState newState){
        if (newState != GameState.RUNNING){
            moveTimer.stop();
            heldDirection = null;
            leftPressed = false;
            rightPressed = false;
        }
        switch (newState){
            case RUNNING -> {
                try{
                    if (MusicPlayer.isMenuMusic){
                        MusicPlayer.isMenuMusic = false;
                        MusicPlayer.stop();
                    }
                    MusicPlayer.startGame();
                } catch (AudioException e) {
                    showErrorToUser("[ERROR] Cannot start music");
                    System.err.println("Audio error: " + e.getMessage());
                }

                if (gameModel.getState() == GameState.NOT_STARTED ||
                        gameModel.getState() == GameState.GAME_OVER){
                    savedScore = 0;
                    timer.setDelay(STANDARD_SPEED);
                    isRecord = false;
                    gameModel.startGame();
                }
                timer.start();
                if (gameFrame != null){
                    gameFrame.showGame();
                }
            }
            case NOT_STARTED -> {
                if (!MusicPlayer.isMenuMusic){
                    MusicPlayer.stop();
                    MusicPlayer.startMenu();
                    MusicPlayer.isMenuMusic = true;
                }
                timer.stop();
                if (gameFrame != null){
                    gameFrame.showStart();
                }
            }case GAME_OVER -> {
                if (!MusicPlayer.isMenuMusic){
                    MusicPlayer.stop();
                    MusicPlayer.startMenu();
                    MusicPlayer.isMenuMusic = true;
                }
                timer.stop();
                savedScore = gameModel.getScore();
                isRecord = recordTable != null && recordTable.isRecord(savedScore);

                if (gameFrame != null){
                    gameFrame.showGameOver();
                }


            }case PAUSED -> {
                MusicPlayer.pause();
                timer.stop();
                if (gameFrame != null){
                    gameFrame.showMenu();
                }
            }case SCOREBOARD -> {
                timer.stop();
                if (gameFrame != null){
                    gameFrame.showScore();
                }
            }case SAVE_RECORD -> {
                if (gameFrame != null){
                    gameFrame.showSaveScore();
                }
            }
            default -> {
                showErrorToUser("[ERROR] Unknown state of game");
                System.err.println("[Controller ERROR] Unknown state of game");
                setGameState(GameState.NOT_STARTED);
            }
        }
        gameModel.setState(newState);
    }

    public void tick(){
            if (gameModel.getState() != GameState.RUNNING){
                timer.stop();
                return;
            }
            GameState stateAfterTick;
            try{
                stateAfterTick = gameModel.tick();
            }catch (BoardException e){
                System.err.println("[BOARD ERROR] " + e.getMessage());
                showErrorToUser("Извините, произошла ошибка наша команда (я :3) обязательно исправит ее");
                stateAfterTick = GameState.NOT_STARTED;
            }catch (ModelException e){
                System.err.println("[MODEL ERROR] " + e.getMessage());
                stateAfterTick = gameModel.getState();
            }


            if (stateAfterTick == GameState.GAME_OVER){
                setGameState(GameState.GAME_OVER);
                timer.stop();
            }

            if (gamePanel != null){
                gamePanel.repaint();
            }
            timer.setDelay(gameModel.getSpeed());
    }

    public void handleKeyPressed(int keyCode){
        try{
            switch(gameModel.getState()){
                case RUNNING -> handleKeyGame(keyCode);
                case PAUSED -> handlePausedKey(keyCode);
            }
        }catch (ModelException e){
            System.err.println(e.getMessage());
        }
    }

    public void handleKeyGame(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_LEFT ->{
                if (!leftPressed){
                    leftPressed = true;
                    rightPressed = false;
                    heldDirection = Direction.LEFT;
                    gameModel.move(Direction.LEFT);
                    moveTimer.restart();
                }
            }
            case KeyEvent.VK_RIGHT ->{
                if (!rightPressed) {
                    rightPressed = true;
                    leftPressed = false;
                    heldDirection = Direction.RIGHT;

                    gameModel.move(Direction.RIGHT);
                    moveTimer.restart();
                }
            }
            case KeyEvent.VK_DOWN ->{
                gameModel.move(Direction.DOWN);
            }
            case KeyEvent.VK_SPACE ->{
                gameModel.drop();
            }
            case KeyEvent.VK_ESCAPE ->{
                moveTimer.stop();
                heldDirection = null;
                leftPressed = false;
                rightPressed = false;
                setGameState(GameState.PAUSED);
            }
            case KeyEvent.VK_W ->{
                gameModel.rotate();
            }
            case KeyEvent.VK_Q -> {
                gameModel.rotateCounterClock();
            }
        }

        gamePanel.repaint();
    }

    private void handlePausedKey(int keyCode){
        if (keyCode == KeyEvent.VK_ESCAPE){
            setGameState(GameState.RUNNING);
            if (gameFrame != null){
                gameFrame.showGame();
            }
        }
    }
    public void handleKeyReleased(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_LEFT -> {
                leftPressed = false;
                if (heldDirection == Direction.LEFT){
                    heldDirection = null;
                    moveTimer.stop();
                }
            }
            case KeyEvent.VK_RIGHT -> {
                rightPressed = false;
                if (heldDirection == Direction.RIGHT){
                    heldDirection = null;
                    moveTimer.stop();
                }
            }
        }
    }
}
