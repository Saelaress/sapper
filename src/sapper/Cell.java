package sapper;

import java.util.ArrayList;
import java.util.Iterator;

public class Cell {

    /**
     * sapper.Mine
     */
    private Mine mine;

    public Mine getMine() {
        return mine;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }


    /**
     * Neighbor cells
     */
    protected ArrayList <Cell> neighborCells = new ArrayList<Cell>();

    private Iterator <Cell> neighboorCellsIt = neighborCells.iterator();

    /**
     * NeighboringMines
     */
    private int numOfNeighboringMines = 0;

    public int getNumOfNeighboringMines() {
        return numOfNeighboringMines;
    }

    private void incNumOfNeighboringMines() {

    }

    private void calcNumberOfNeighboringMines() {

    }

    /**
     * sapper.State
     */
    private State state;

    public State getState() {
        return state;
    }

    private void setState(State state) {

    }

    /**
     * Flag
     */
    public boolean setFlag() {
        return false;
    }

    private boolean isFlag() {
        return false;
    }

    public boolean removeFlag() {
        return false;
    }

    /**
     * Opening and checking
     */
    public boolean open() {
        return false;
    }

    private boolean isOpen() {
        return false;
    }


    public boolean openIfEmpty() {
        return false;
    }

    private boolean canOpen() {
        return false;
    }

    private boolean isMined() {
        return false;
    }
}
