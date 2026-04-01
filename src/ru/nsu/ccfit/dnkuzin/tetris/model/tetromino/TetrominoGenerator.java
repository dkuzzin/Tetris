package ru.nsu.ccfit.dnkuzin.tetris.model.tetromino;

import java.util.*;

public class TetrominoGenerator {
    static private final ArrayList<TetrominoType> bag = new ArrayList<>();
    private static void fillBag(){
        bag.clear();
        Collections.addAll(bag, TetrominoType.values());
        Collections.shuffle(bag);
    }
    public static TetrominoType getRandomTetrominoType(){
        if (bag.isEmpty()){
            fillBag();
        }
        TetrominoType result = bag.getFirst();
        bag.removeFirst();
        return result;
    }
}
