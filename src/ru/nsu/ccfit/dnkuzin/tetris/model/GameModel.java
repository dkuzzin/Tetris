package ru.nsu.ccfit.dnkuzin.tetris.model;

import ru.nsu.ccfit.dnkuzin.tetris.exceptions.BoardException;
import ru.nsu.ccfit.dnkuzin.tetris.exceptions.ModelException;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.TetrominoGenerator;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.TetrominoType;

import java.util.ArrayList;

public class GameModel {
    private static final int[] LEVEL_DELAYS = {
            1000, 793, 618, 473, 355,
            262, 190, 135, 94, 64, 43
    };
    private final ArrayList<Integer> linesForDelete = new ArrayList<>();
    private boolean clearing = false;
    private GameState state;
    private int level, speed, score;
    private int removedLinesCount;
    private Tetromino currTetromino, nextTetromino;

    private Board board;

    public GameModel(){
        board = new Board();
        state = GameState.NOT_STARTED;
    }
    public int getScore(){return score;}
    public int getSpeed(){return speed;}
    public int getLevel(){return (level+1);}
    public Tetromino getCurrTetromino() {return currTetromino;}
    public Board getBoard() {return board;}
    public GameState getState() {return state;}
    public void setState(GameState newState) {this.state = newState;}

    public void startGame(){
        score = 0;
        level = 0;
        removedLinesCount = 0;
        speed = LEVEL_DELAYS[0];

        board = new Board();
        state = GameState.RUNNING;

        nextTetromino = spawn(TetrominoGenerator.getRandomTetrominoType());
        currTetromino = spawn(TetrominoGenerator.getRandomTetrominoType());
    }

    private void updateLevel(){
        while (removedLinesCount >= 10){
            level++;
            if (level < LEVEL_DELAYS.length) speed = LEVEL_DELAYS[level];
            removedLinesCount -= 10;
        }
    }

    private void addScore(int removedLines) {
        int base = switch (removedLines) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 500;
            case 4 -> 800;
            case 5 -> 1000;
            case 6, 7, 8, 9, 10 -> 1200;
            default -> 0;
        };
        score += base * (level+1);
    }
    private void updateSpeed() throws ModelException{
        speed = LEVEL_DELAYS[level];
    }
    private Tetromino spawn(TetrominoType type) {
        int spawnY = 0;

        int spawnX = type == TetrominoType.O ? 4 : 3;

        return new Tetromino(type, spawnX, spawnY);
    }
    public GameState tick() throws ModelException, BoardException {
        if (state != GameState.RUNNING){
            throw new ModelException("Can't do tick, because game is not running");
        }


        if (!board.canMove(currTetromino, Direction.DOWN)){
            board.lockPiece(currTetromino);


            int removedNow = board.clearFullLines();
            addScore(removedNow);

            removedLinesCount += removedNow;
            updateLevel();
            updateSpeed();

            currTetromino = nextTetromino;
            nextTetromino = spawn(TetrominoGenerator.getRandomTetrominoType());

            if (!board.canPlace(currTetromino)){
                return GameState.GAME_OVER;
            }
            return GameState.RUNNING;
        }
        currTetromino.move(Direction.DOWN.dx, Direction.DOWN.dy);

        return GameState.RUNNING;
    }
    public void move(Direction direction){
        if (board.canMove(currTetromino, direction)){
            currTetromino.move(direction.dx, direction.dy);
        }
    }

    public void drop(){
        while (board.canMove(currTetromino, Direction.DOWN)){
            currTetromino.move(Direction.DOWN.dx, Direction.DOWN.dy);
        }
    }
    public void rotate(){
        board.tryRotate(currTetromino, true);
    }
    public void rotateCounterClock(){
        board.tryRotate(currTetromino, false);
    }
}
