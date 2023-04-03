package sapper.environments;

import sapper.Mine;

import java.awt.*;

public class EnvironmentFirst extends Environment_generator{

    private static final int FIELD_HEIGHT = 3;
    private static final int FIELD_WIDTH = 3;

    @Override
    protected void addMines() {
        Mine firstMine = new Mine();
        Mine secondMine = new Mine();

        field.getCell(new Point(0,2)).setMine(firstMine);
        field.getCell(new Point(2,0)).setMine(secondMine);
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
