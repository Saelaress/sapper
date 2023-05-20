package sapper;
import java.util.ListIterator;

public class Wall extends Item{

    Cell cell;

    public Wall(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void open() {
        this.opened = true;
        openAllSegments();
    }

    private void openAllSegments() {
        ListIterator<Cell> neighboringCellIt = cell.neighborCells.listIterator();
        while (neighboringCellIt.hasNext()) {
            Cell nc = neighboringCellIt.next();
            if(!nc.isOpen() && !nc.isEmpty() && nc.getItem() instanceof Wall) {
                nc.setState(State.OPEN);
                nc.getItem().open();
            }
        }
    }
}
