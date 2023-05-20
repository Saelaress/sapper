package test.environment_generator;

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

        field.getCell(new Point(1,0)).setItem(new Wall(field.getCell(new Point(0,0))));
        field.getCell(new Point(1,1)).setItem(new Wall(field.getCell(new Point(1,1))));

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

    @Override
    protected int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    protected int fieldWidth() {
        return FIELD_WIDTH;
    }

}
