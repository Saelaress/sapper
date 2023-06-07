package sapper;

import java.util.ListIterator;

public class Mine_detector extends Item{

    Cell cell;


    public Mine_detector(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void openItem() {
        this.opened = true;
        if(!cell.isOpen()) {
            cell.setState(State.OPEN);
        }
        openMineInRadius();
    }

    private void openMineInRadius() {
        ListIterator<Cell> neighboringCellIt = cell.neighborCells.listIterator();
        while (neighboringCellIt.hasNext()) {
            Cell nc = neighboringCellIt.next();
            if(!nc.isOpen() && nc.getItem() instanceof Mine){
                nc.setState(State.OPEN);
            }
        }
    }
}
