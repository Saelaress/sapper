package sapper.environments;

import sapper.*;

import java.awt.*;

public class EnvironmentRandom extends Environment_generator {

    private static int FIELD_HEIGHT;
    private static int FIELD_WIDTH;

    @Override
    public Field buildField() {
        field = new Field(fieldWidth(), fieldHeight());

        addMine_detector();
        addWalls();
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

    protected void addWalls() {
        Cell cell1 = field.getCell(new Point(1,0));
        Cell cell2 = field.getCell(new Point(1,1));
        Cell cell3 = field.getCell(new Point(0,0));
        Cell cell4 = field.getCell(new Point(3,3));
        Cell cell5 = field.getCell(new Point(2,0));
        Wall w1 = new Wall(cell1);
        Wall w2 = new Wall(cell2);
        Wall w3 = new Wall(cell3);
        Wall w4 = new Wall(cell4);
        Wall w5 = new Wall(cell5);

        cell1.setItem(w1);
        cell2.setItem(w2);
        cell3.setItem(w3);
        cell4.setItem(w4);
        cell5.setItem(w5);
        field.setupNeighborWalls();
    }

    protected void addMine_detector() {
        Cell cell1 = field.getCell(new Point(5,5));

        Mine_detector mine_detector = new Mine_detector(cell1);

        cell1.setItem(mine_detector);
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