package sapper;

import sapper.event.CellActionEvent;
import sapper.event.CellActionListener;

import java.util.ArrayList;
import java.util.ListIterator;

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
        ListIterator<Cell> neighboringCellIt = neighborCells.listIterator();
        while (neighboringCellIt.hasNext()) {
            if (neighboringCellIt.next().isMined() == true) {
                incNumOfNeighboringMines();
            }
        }
        if(numOfNeighboringMines == 0) {
            while (neighboringCellIt.hasPrevious()) {
                neighboringCellIt.previous().openIfEmpty();
            }
        }
    }

    /**
     * sapper.State
     */
    private State state = State.CLOSE;

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
        if(canOpen()) {
            if(isMined()) {
                mine.detonate();
                setState(State.OPEN);
                fireCellIsOpen(this);
                return true;
            }
            setState(State.OPEN);
            calcNumberOfNeighboringMines();
            //ячейка открыта - событие
            fireCellIsOpen(this);
            return true;
        }
        else return false;
    }

    private boolean isOpen() {
        if(getState() == State.OPEN) return true;
        else return false;
    }


    public boolean openIfEmpty() {
        if(canOpen() == true && isMined() == false) {
            setState(State.OPEN);
            calcNumberOfNeighboringMines();
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
    private ArrayList<CellActionListener> cellListListener = new ArrayList<>();

    public void addCellActionListener(CellActionListener listener) {
        cellListListener.add(listener);
    }

    public void removeCellActionListener(CellActionListener listener) {
        cellListListener.remove(listener);
    }
    private void fireCellIsOpen(Cell cell) {
        for(CellActionListener listener: cellListListener) {
            CellActionEvent event = new CellActionEvent(listener);
            event.setCell(cell);
            listener.cellIsOpen(event);
        }
    }


}
