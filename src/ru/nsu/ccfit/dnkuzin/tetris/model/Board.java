package ru.nsu.ccfit.dnkuzin.tetris.model;

import ru.nsu.ccfit.dnkuzin.tetris.exceptions.BoardException;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.dnkuzin.tetris.model.tetromino.TetrominoType;


public class Board {
    private final int sizeX = 10;
    private final int sizeY = 20;

    private final TetrominoType[][] area;
    public Board(){
        area = new TetrominoType[sizeY][sizeX];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
    public TetrominoType getCell(int x, int y) throws BoardException{
        if (!isValid(x, y)){
            throw new BoardException("Invalid x and y: " + x + " " + y);
        }
        return area[y][x];
    }

    private boolean isValid(int x, int y){
        return x < sizeX && x >= 0
                && y < sizeY && y >= 0;
    }

    public boolean canPlace(Tetromino tetromino){
        Coordinate[] cells = tetromino.getGlobalCells();
        for (Coordinate i: cells){
            if (!isValid(i.x(), i.y()) || area[i.y()][i.x()] != null){
                return false;
            }
        }
        return true;
    }


    public void lockPiece(Tetromino tetromino) throws BoardException{
        if (!canPlace(tetromino)){
            throw new BoardException("[BOARD error] Cannot lock tetromino but we need");
        }
        Coordinate[] cells = tetromino.getGlobalCells();
        for (Coordinate i: cells){
            area[i.y()][i.x()] = tetromino.getType();
        }
    }
    private boolean isLineFull(int y){
        for (int x = 0; x < sizeX; x++){
            if (area[y][x] == null){
                return false;
            }
        }
        return true;
    }
    private void dropLines(int line){
        if (line == 0){
            for (int x = 0; x < sizeX; x++){
                area[line][x] = null;
            }
            return;
        }
        for (int x = 0; x < sizeX; x++){
            area[line][x] = area[line-1][x];
        }
        dropLines(line-1);
    }
    public int clearFullLines(){
        int count = 0;
        for (int line = sizeY-1; line >= 0;){
            if (isLineFull(line)){
                count++;
                dropLines(line);
            }else{
                line--;
            }
        }
        return count;
    }

    public boolean canMove(Tetromino tetromino, Direction direction){
        Coordinate[] cells = tetromino.getGlobalCells();

        for (Coordinate i: cells){
            int newX = i.x() + direction.dx;
            int newY = i.y() + direction.dy;

            if (!isValid(newX, newY) || area[newY][newX] != null){
                return false;
            }
        }
        return true;
    }



    private int[][] getKickOffsets(Tetromino tetromino) {
        switch (tetromino.getType()) {
            case I:
                return new int[][]{
                        {0, 0},
                        {-1, 0},
                        {1, 0},
                        {-2, 0},
                        {2, 0},
                        {0, -1}
                };
            case O:
                return new int[][]{
                        {0, 0}
                };
            default:
                return new int[][]{
                        {0, 0},
                        {-1, 0},
                        {1, 0},
                        {0, -1},
                        {-1, -1},
                        {1, -1}
                };
        }
    }

    public boolean canPlace(Tetromino tetromino, int x, int y, int rotation) {
        Coordinate[] cells = tetromino.getGlobalCells(x, y, rotation);

        for (Coordinate cell : cells) {
            int newX = cell.x();
            int newY = cell.y();

            if (!isValid(newX, newY) || area[newY][newX] != null) {
                return false;
            }
        }
        return true;
    }
    public boolean tryRotate(Tetromino tetromino, boolean clockwise){
        int next_rotation = clockwise? tetromino.getNextClockwiseRotation() :
                tetromino.getNextCounterClockwiseRotation();

        int[][] kicks = getKickOffsets(tetromino);
        for (int[] kick : kicks){
            int newX = tetromino.getX() + kick[0];
            int newY = tetromino.getY() + kick[1];
            if (canPlace(tetromino, newX, newY, next_rotation)){
                tetromino.setPosition(newX, newY);
                tetromino.setRotation(next_rotation);
                return true;
            }
        }


        return false;
    }
}
