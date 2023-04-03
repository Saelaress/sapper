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

    private Iterator <Cell> minedCellsIt;

    public Field(int width, int height){

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean areAllEmptyCellsOpen() {
        return false;
    }

    public void openMinedCells() {

    }

    private Iterator getMinedCells() {
        return minedCellsIt;
    }

    private Iterator getEmptyCellsList() {
        return minedCellsIt;
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
