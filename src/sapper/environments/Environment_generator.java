package sapper.environments;

import sapper.Field;

public abstract class Environment_generator {

    protected Field field;

    public abstract Field buildField();

    protected abstract void addMines();

    protected abstract int fieldHeight();

    protected abstract int fieldWidth();

}
