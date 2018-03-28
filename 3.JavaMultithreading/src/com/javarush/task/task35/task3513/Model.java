package com.javarush.task.task35.task3513;

import java.util.*;

/**
 * Created by Mikhail Shamanin on 22.12.2017.
 */
public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    protected int score;
    protected int maxTile;
    private boolean isSaveNeeded = true;
    private Stack<Tile[][]> previousStates=new Stack<>();
    private Stack<Integer> previousScores=new Stack<>();

    public Model() {
        resetGameTiles();
    }


    private List<Tile> getEmptyTiles() {
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) result.add(gameTiles[i][j]);
            }
        }
        return result;
    }

    private void addTile() {
        List<Tile> result = getEmptyTiles();
        if (!result.isEmpty()) {
            int val = (Math.random() < 0.9 ? 2 : 4);
            int probPos = (int) (Math.random()*result.size());
            result.get(probPos).value = val;
        }
    }

    public void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
        score = 0;
        maxTile = 2;
    }

    private boolean compressTiles(Tile[] tiles) {
        Tile[] forComparing = Arrays.copyOf(tiles, FIELD_WIDTH);
        int count = 0;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (tiles[i].isEmpty() && (i < FIELD_WIDTH - 1) && count < FIELD_WIDTH) {
                count++;
                Tile tileTmp = tiles[i];
                for (int j = i + 1; j < FIELD_WIDTH; j++) {
                    tiles[j - 1] = tiles[j];
                }
                tiles[FIELD_WIDTH - 1] = tileTmp;
                i--;
            }
        }
        if (Arrays.equals(forComparing, tiles)) {
            return false;
        } else return true;
    }

    private boolean mergeTiles(Tile[] tiles) {
        Tile[] forComparing = Arrays.copyOf(tiles, FIELD_WIDTH);
        for (int i = 0; i < FIELD_WIDTH - 1; i++) {
            if (tiles[i].value == tiles[i + 1].value) {
                int mergeScore = tiles[i].value * 2;
                score += mergeScore;
                if (maxTile < mergeScore) {
                    maxTile = mergeScore;
                }
                tiles[i] = new Tile(mergeScore);
                for (int j = i + 1; j < FIELD_WIDTH - 1; j++) {
                    tiles[j] = tiles[j + 1];
                }
                tiles[FIELD_WIDTH - 1] = new Tile();
            }
        }
        if (Arrays.equals(forComparing, tiles)) {
            return false;
        } else {
            return true;
        }
    }

    public void left() {
        if (isSaveNeeded){
            saveState(this.gameTiles);
        }
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i])) {
                isChanged = true;
            }
            if (mergeTiles(gameTiles[i])) {
                isChanged = true;
            }
        }
        if (isChanged) {
            addTile();
            isSaveNeeded = true;
        }
    }

    public void right() {
        saveState(this.gameTiles);
        rotate(gameTiles);
        rotate(gameTiles);
        left();
        rotate(gameTiles);
        rotate(gameTiles);
    }

    public void up() {
        saveState(this.gameTiles);
        rotate(gameTiles);
        rotate(gameTiles);
        rotate(gameTiles);
        left();
        rotate(gameTiles);
    }

    public void down() {
        saveState(this.gameTiles);
        rotate(gameTiles);
        left();
        rotate(gameTiles);
        rotate(gameTiles);
        rotate(gameTiles);
    }

    public void rotate(Tile[][] tiles) {
        Tile[][] tmpArr = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++){
            tmpArr[i] = Arrays.copyOf(tiles[i], FIELD_WIDTH);
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tiles[i][j] = tmpArr[FIELD_WIDTH - j - 1][i];
            }
        }
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {
        if (!getEmptyTiles().isEmpty())
            return true;
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 1; j < gameTiles.length; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j - 1].value)
                    return true;
            }
        }
        for (int j = 0; j < gameTiles.length; j++) {
            for (int i = 1; i < gameTiles.length; i++) {
                if (gameTiles[i][j].value == gameTiles[i - 1][j].value)
                    return true;
            }
        }
        return false;
    }

    private boolean equalsTiles(Tile[][] t1, Tile[][] t2) {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (!t1[i][j].equals(t2[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void saveState (Tile[][] tiles) {
        Tile[][] tmpArr = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++){
            tmpArr[i] = Arrays.copyOf(tiles[i], FIELD_WIDTH);
        }
        previousStates.push(tmpArr);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
            this.gameTiles = previousStates.pop();
            this.score = previousScores.pop();
        }
    }

    public void randomMove(){
        int  n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    public boolean hasBoardChanged(){
        return !equalsTiles(previousStates.peek(), this.getGameTiles());
    }


    public MoveEfficiency getMoveEfficiency(Move move){
        MoveEfficiency result;
        move.move();
        if (!hasBoardChanged()) {
            result = new MoveEfficiency(-1, 0, move);
        } else {
            result = new MoveEfficiency(getEmptyTiles().size(), score, move);
        }
        rollback();
        return result;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> pQ = new PriorityQueue(4, Collections.reverseOrder());
        pQ.add(this.getMoveEfficiency(this::left));
        pQ.add(this.getMoveEfficiency(this::right));
        pQ.add(this.getMoveEfficiency(this::up));
        pQ.add(this.getMoveEfficiency(this::down));
        pQ.peek().getMove().move();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                sb.append(String.format("%5s", gameTiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
