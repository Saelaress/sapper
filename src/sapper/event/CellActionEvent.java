package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Cell;
import java.util.EventObject;

public class CellActionEvent extends EventObject {
    private Cell cell;
    private boolean isMined;

    public void setCell(@NotNull Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setMined(@NotNull boolean isMined) { this.isMined = isMined; }

    public boolean getMined() {
        return this.isMined;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CellActionEvent(Object source) {
        super(source);
    }
}
