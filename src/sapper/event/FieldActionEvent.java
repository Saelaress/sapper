package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Cell;
import sapper.Mine;

public class FieldActionEvent {
    private Mine mine;
    private Cell cell;
    private boolean isMined;

    public void setMine(@NotNull Mine mine) {
        this.mine = mine;
    }
    public void setCell(@NotNull Cell cell) {
        this.cell = cell;
    }
    public Mine getMine() {
        return mine;
    }
    public Cell getCell() {
        return cell;
    }
    public void setMined(boolean isMined) { this.isMined = isMined; }
    public boolean getMined() { return isMined; }


    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FieldActionEvent(Object source) {
        super();
    }
}
