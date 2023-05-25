package sapper;

import sapper.event.CellActionEvent;
import sapper.event.CellActionListener;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Класс Ячейки
 */
public class Cell {


    private Item item;

    private boolean isEmpty = true;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        isEmpty = false;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public ArrayList <Cell> neighborCells = new ArrayList<Cell>();

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
            Cell nc = neighboringCellIt.next();
            if (!nc.isEmpty() && nc.getItem() instanceof Mine) {
                incNumOfNeighboringMines();
            }
        }
        if(numOfNeighboringMines == 0) {
            while (neighboringCellIt.hasPrevious()) {
                neighboringCellIt.previous().openIfEmpty();
            }
        }
    }

    private State state = State.CLOSE;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean setFlag() {
        if(isFlag() || isOpen()) {
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

    public boolean open() {
        if(canOpen()) {
            if(!isOpen()) {
                if(!isEmpty()) {
                    item.openItem();
                    setState(State.OPEN);
                    fireCellIsOpen(this, true);
                    return true;
                }
                setState(State.OPEN);
                calcNumberOfNeighboringMines();
                //ячейка открыта - событие
                fireCellIsOpen(this, false);
            }
            return true;
        }
        else return false;
    }

    public boolean isOpen() {
        if(getState() == State.OPEN) return true;
        else return false;
    }

    public boolean openIfEmpty() {
        if(canOpen() && isEmpty()) {
            setState(State.OPEN);
            calcNumberOfNeighboringMines();
            return true;
        }
        else return false;
    }

    private boolean canOpen() {
        return !((!isOpen() && isFlag()) || isOpen());
    }


    // -------------------- События --------------------
    private ArrayList<CellActionListener> cellListListener = new ArrayList<>();

    public void addCellActionListener(CellActionListener listener) {
        cellListListener.add(listener);
    }

    public void removeCellActionListener(CellActionListener listener) {
        cellListListener.remove(listener);
    }
    private void fireCellIsOpen(Cell cell, boolean isMined) {
        for(CellActionListener listener: cellListListener) {
            CellActionEvent event = new CellActionEvent(listener);
            event.setCell(cell);
            event.setMined(isMined);
            listener.cellIsOpen(event);
        }
    }
}
