package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        int result = 0;
        result = Integer.compare(numberOfEmptyTiles, o.numberOfEmptyTiles);
        if(result != 0) return result;
        result = Integer.compare(score, o.score);
        if(result != 0) return result;
        return 0;
    }
}
