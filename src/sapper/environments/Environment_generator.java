package sapper.environments;

import sapper.Field;

public abstract class Environment_generator {

    protected Field field;

    public Field buildField() {

        field = new Field(fieldWidth(), fieldHeight());

        addMines();

        return field;
    }

    protected abstract void addMines();

    protected abstract int fieldHeight();

    protected abstract int fieldWidth();

}
