package sapper;

/**
 * Класс содержимого ячейки
 */
public abstract class Item {
    boolean opened = false;

    public boolean isOpened() {
        return opened;
    }

    public abstract void openItem();
}
