package sapper.environments;

public class EnvironmentFirst extends Environment_generator{

    private static final int FIELD_HEIGHT = 3;
    private static final int FIELD_WIDTH = 3;

    @Override
    protected void addMines() {

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
