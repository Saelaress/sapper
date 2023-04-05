package sapper;

import sapper.event.FieldActionEvent;
import sapper.event.FieldActionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Field {

    private Map<Point, Cell> cells = new HashMap<>();

    private int width;
    private int height;

    public Field(int width, int height) {
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");

        this.width = width;
        this.height = height;

        setupField();

        // Subscribe on mines
        //TODO
        //((ExitCell) getCell(exitPoint)).addExitCellActionListener(new ExitCellObserver());
    }

    private void setupField() {
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Point p = new Point(x, y);
                Cell cell =  new Cell();

                if(x > 0) {
                    Cell neighboorCellEast = getCell(new Point((int) (p.getX() - 1), (int) p.getY()));
                    neighboorCellEast.neighborCells.add(cell);
                    cell.neighborCells.add(neighboorCellEast);
                }
                if(y > 0) {
                    Cell neighboorCellNorth = getCell(new Point((int) p.getX(), (int) (p.getY() - 1)));
                    neighboorCellNorth.neighborCells.add(cell);
                    cell.neighborCells.add(neighboorCellNorth);

                    //Если есть ячейка на северозападе
                    if(x > 0) {
                        Cell neighboorCellNW = getCell(new Point((int) (p.getX()-1), (int) (p.getY()-1)));
                        neighboorCellNW.neighborCells.add(cell);
                        cell.neighborCells.add(neighboorCellNW);
                    }

                    //Если есть ячейка на северовостоке
                    if(x < width - 1) {
                        Cell neighboorCellNE = getCell(new Point((int) (p.getX()+1), (int) (p.getY()-1)));
                        neighboorCellNE.neighborCells.add(cell);
                        cell.neighborCells.add(neighboorCellNE);
                    }
                }

                cells.put(p, cell);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean areAllEmptyCellsOpen() {
        //TODO
        return false;
    }

    public void openMinedCells() {
        //TODO
    }

    private Iterator getMinedCells() {
        ArrayList <Cell> minedCells = new ArrayList<>();
        for(var i : cells.entrySet()) {
            Mine mine = i.getValue().getMine();
            if(mine != null) minedCells.add(i.getValue());
        }

        Iterator <Cell> minedCellsIterator = minedCells.iterator();
        return minedCellsIterator;
    }

    private Iterator getEmptyCells() {
        ArrayList <Cell> emptyCells = new ArrayList<>();
        for(var i : cells.entrySet()) {
            Mine mine = i.getValue().getMine();
            if(mine == null) emptyCells.add(i.getValue());
        }

        Iterator <Cell> emptyCellsIterator = emptyCells.iterator();
        return emptyCellsIterator;
    }

    public Cell getCell(Point point){
        return cells.get(point);
    }

    // -------------------- События --------------------

    private ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    public void addFieldlActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    public void removeFieldCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    private void fireMineIsDetonated(Mine mine) {
        for(FieldActionListener listener: fieldListListener) {
            FieldActionEvent event = new FieldActionEvent(listener);
            event.setMine(mine);
            listener.mineIsDetonated(event);
        }
    }
}
