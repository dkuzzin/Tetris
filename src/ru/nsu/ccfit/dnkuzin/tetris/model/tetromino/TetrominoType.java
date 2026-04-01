package ru.nsu.ccfit.dnkuzin.tetris.model.tetromino;

import ru.nsu.ccfit.dnkuzin.tetris.model.Coordinate;

public enum TetrominoType {
    I(new Coordinate[][]{
            {
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
                    new Coordinate(3,1),
            },
            {
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(1,2),
                    new Coordinate(1,3),
            }
    }),
    O(new Coordinate[][]{
        {
                    new Coordinate(1,0),
                    new Coordinate(2,0),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
        }
    }),
    T(new Coordinate[][]{
            {
                    new Coordinate(1,0),
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
            },
            {
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(1,2),
                    new Coordinate(2,1),
            },
            {
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
                    new Coordinate(1,2),
            },
            {
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(1,2),
                    new Coordinate(0,1),
            }
    }),
    L(new Coordinate[][]{
            {
                    new Coordinate(2,0),
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
            },
            {
                    new Coordinate(0,0),
                    new Coordinate(0,1),
                    new Coordinate(0,2),
                    new Coordinate(1,2),
            },
            {
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
                    new Coordinate(0,2),
            },
            {
                    new Coordinate(0,0),
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(1,2),
            }
    }),
    J(new Coordinate[][]{
            {
                    new Coordinate(0,0),
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
            },
            {
                    new Coordinate(0,0),
                    new Coordinate(1,0),
                    new Coordinate(0,1),
                    new Coordinate(0,2),
            },
            {
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
                    new Coordinate(2,2),
            },
            {
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(0,2),
                    new Coordinate(1,2),
            }
    }),
    S(new Coordinate[][]{
        {
                    new Coordinate(1,0),
                    new Coordinate(2,0),
                    new Coordinate(0,1),
                    new Coordinate(1,1),
        },
        {
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
                    new Coordinate(2,2),
        }
    }),
    Z(new Coordinate[][]{
        {
                    new Coordinate(0,0),
                    new Coordinate(1,0),
                    new Coordinate(1,1),
                    new Coordinate(2,1),
        },
            {
                    new Coordinate(1,0),
                    new Coordinate(0,1),
                    new Coordinate(1,1),
                    new Coordinate(0,2),
            }
    });

    private final Coordinate[][] rotations;
    TetrominoType(Coordinate[][] rotations) {
        this.rotations = rotations;
    }

    public Coordinate[] getCells(int rotation){
        return rotations[rotation];
    }

    public int getRotationCount(){
        return rotations.length;
    }

}
