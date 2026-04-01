package ru.nsu.ccfit.dnkuzin.tetris.model.tetromino;

import ru.nsu.ccfit.dnkuzin.tetris.model.Coordinate;

public class Tetromino {
    private final TetrominoType type;
    private int rotation;

    private int x;
    private int y;

    public Tetromino(TetrominoType type, int startX, int startY){
        this.type = type;
        rotation = 0;
        x = startX;
        y = startY;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    public TetrominoType getType(){
        return type;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNextClockwiseRotation(){
        return (rotation + 1) % type.getRotationCount();
    }
    public int getNextCounterClockwiseRotation(){
        return (rotation - 1 + type.getRotationCount()) % type.getRotationCount();
    }


    public Coordinate[] getLocalCells(){
        return type.getCells(rotation);
    }

    public Coordinate[] getGlobalCells(){
         Coordinate[] local = this.getLocalCells();
         int localLength = local.length;
         Coordinate[] global = new Coordinate[localLength];

         for (int i = 0; i < localLength; i++){
            global[i] = new Coordinate(x + local[i].x(), y + local[i].y());
         }
         return global;
    }

    public Coordinate[] getGlobalCells(int x, int y, int nextRotation){

        Coordinate[] local = type.getCells(nextRotation);
        Coordinate[] global = new Coordinate[local.length];

        for (int i = 0; i < local.length; i++){
            global[i] = new Coordinate(x + local[i].x(), y + local[i].y());
        }
        return global;
    }

    public void rotateClockwise(){
        rotation = (rotation + 1) % type.getRotationCount();
    }

    public void rotateCounterclockwise(){
        int length = type.getRotationCount();
        rotation = (rotation - 1 + length) % length;
    }


    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }
}
