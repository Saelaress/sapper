package sapper;
import java.util.ArrayList;
import java.util.ListIterator;

public class Wall extends Item{

    Cell cell;

    public ArrayList<Wall> neighborWalls = new ArrayList<>();

    public Wall(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void openItem() {
        this.opened = true;
        if(!cell.isOpen()) {
            cell.setState(State.OPEN);
        }
        openAllSegments();
    }

    private void openAllSegments() {
        ListIterator<Wall> neighboringWallIt = neighborWalls.listIterator();
        while (neighboringWallIt.hasNext()) {
            Wall nw = neighboringWallIt.next();
            if(!nw.isOpened()){
                nw.openItem();
            }
        }
    }
}
