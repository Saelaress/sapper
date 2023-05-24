package test.environment_generator;

import sapper.Cell;
import sapper.Field;
import sapper.Mine;
import sapper.Wall;
import sapper.environments.Environment_generator;

import java.awt.*;

public class TestEnvironment_generator extends Environment_generator {

    private static final int FIELD_HEIGHT = 3;
    private static final int FIELD_WIDTH = 3;

    @Override
    public Field buildField() {
        field = new Field(fieldWidth(), fieldHeight());

        addMines();
        addWalls();

        return field;
    }

    @Override
    protected void addMines() {
        Mine firstMine = new Mine();
        Mine secondMine = new Mine();
        Mine thirdMine = new Mine();

        field.getCell(new Point(0,2)).setItem(firstMine);
        field.getCell(new Point(2,0)).setItem(secondMine);
        field.getCell(new Point(2,2)).setItem(thirdMine);
    }

    protected void addWalls() {
        Cell cell1 = field.getCell(new Point(1,0));
        Cell cell2 = field.getCell(new Point(1,1));
        Wall fw = new Wall(cell1);
        Wall sw = new Wall(cell2);
        fw.neighborWalls.add(sw);
        sw.neighborWalls.add(fw);

        cell1.setItem(fw);
        cell2.setItem(sw);
    }

    @Override
    protected int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    protected int fieldWidth() {
        return FIELD_WIDTH;
    }

}
