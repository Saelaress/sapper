package sapper;

import sapper.event.MineActionEvent;
import sapper.event.MineActionListener;

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
    public ArrayList <Cell> neighborCells = new ArrayList<Cell>();

    /**
     * Neighboring Mines
     */
    private int numOfNeighboringMines = 0;

    public int getNumOfNeighboringMines() {
        return numOfNeighboringMines;
    }

    private void incNumOfNeighboringMines() {
        numOfNeighboringMines += 1;
    }

    private void calcNumberOfNeighboringMines() {
        Iterator<Cell> neighboringCellIt = neighborCells.iterator();
        while (neighboringCellIt.hasNext()) {
            if (neighboringCellIt.next().openIfEmpty() == false) {
                incNumOfNeighboringMines();
            }
        }
    }

    /**
     * sapper.State
     */
    private State state;

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    /**
     * Flag
     */
    public boolean setFlag() {
        if(isFlag()) {
            return false;
        }
        else {
            setState(State.FLAG);
            return true;
        }
    }

    private boolean isFlag() {
        if(getState() == State.FLAG) return true;
        else return false;
    }

    public boolean removeFlag() {
        if(isFlag()) {
            setState(State.CLOSE);
            return true;
        }
        else return false;
    }

    /**
     * Opening and checking
     */
    public boolean open() {
        return false;
    }

    private boolean isOpen() {
        if(getState() == State.OPEN) return true;
        else return false;
    }


    public boolean openIfEmpty() {
        if(canOpen() == true && isMined() == false) {
            Iterator <Cell> newNeighboringCellIt = neighborCells.iterator();
            while(newNeighboringCellIt.hasNext()) {
                newNeighboringCellIt.next().openIfEmpty();
            }
            return true;
        }
        else return false;
    }

    private boolean canOpen() {
        if((isOpen() == false && isFlag() == true) || isOpen() == true) {
            return false;
        }
        else return true;
    }

    private boolean isMined() {
        if(mine != null) return true;
        else return false;
    }

    // -------------------- События --------------------
    private ArrayList<MineActionListener> mineListListener = new ArrayList<>();

    public void addMineActionListener(MineActionListener listener) {
        mineListListener.add(listener);
    }

    public void removeExitCellActionListener(MineActionListener listener) {
        mineListListener.remove(listener);
    }

    private void mineIsDetonated(Mine mine) {
        for(MineActionListener listener: mineListListener) {
            MineActionEvent event = new MineActionEvent(listener);
            event.setMine(mine);
            listener.mineIsDetonated(event);
        }
    }
}
