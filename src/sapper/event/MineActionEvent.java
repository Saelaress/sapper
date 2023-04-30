package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Cell;
import sapper.Mine;

import java.util.EventObject;

public class MineActionEvent extends EventObject {
    private Mine mine;
    private Cell cell;

    public void setMine(@NotNull Mine mine) {
        this.mine = mine;
    }

    public Mine getMine() {
        return mine;
    }

    public void setCell(@NotNull Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MineActionEvent(Object source) {
        super(source);
    }
}
