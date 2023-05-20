package sapper.environments;

import sapper.Cell;
import sapper.Field;
import sapper.Mine;

import java.awt.*;

public class EnvironmentRandom extends Environment_generator {

    private static int FIELD_HEIGHT;
    private static int FIELD_WIDTH;

    @Override
    public Field buildField() {
        field = new Field(fieldWidth(), fieldHeight());

        addMines();

        return field;
    }

    @Override
    protected void addMines() {
        for(int i = 0; i <= Math.min(FIELD_HEIGHT, FIELD_WIDTH); i++) {
            Cell cell = findRandomEmptyCell();
            cell.setItem(new Mine());
        }
    }

    private Cell findRandomEmptyCell() {
        //TODO
        boolean findEmpty = false;
        Cell emptyCell;
        do {
            emptyCell = field.getCell(new Point((int) (Math.random() * FIELD_WIDTH),(int) (Math.random() * FIELD_HEIGHT)));
            if(emptyCell.isEmpty()) { findEmpty = true; }
        } while(findEmpty == false);
        return emptyCell;
    }

    @Override
    public int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    public int fieldWidth() {
        return FIELD_WIDTH;
    }

    public void setFieldParam(int height, int width) {
        FIELD_HEIGHT = height;
        FIELD_WIDTH = width;
    }
}