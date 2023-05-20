package sapper.environments;

import sapper.Field;
import sapper.Mine;

import java.awt.*;

public class EnvironmentFirst extends Environment_generator{

    private static final int FIELD_HEIGHT = 5;
    private static final int FIELD_WIDTH = 5;

    @Override
    public Field buildField() {

        field = new Field(fieldWidth(), fieldHeight());

        addMines();

        return field;
    }

    @Override
    protected void addMines() {
        Mine firstMine = new Mine();
        Mine secondMine = new Mine();

        field.getCell(new Point(0,2)).setItem(firstMine);
        field.getCell(new Point(2,0)).setItem(secondMine);
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
