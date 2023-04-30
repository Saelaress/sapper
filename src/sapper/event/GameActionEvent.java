package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Cell;
import sapper.Game_status;

import java.util.EventObject;

public class GameActionEvent extends EventObject {

//    private Sapper sapper;
//
//    public void setSapper(@NotNull Sapper sapper) { this.sapper = sapper; }
//
//    public Sapper getSapper() {
//        return sapper;
//    }

    private Game_status status;
    private Cell cell;
    private boolean isMined;

    public void setStatus(@NotNull Game_status status) { this.status = status; }
    public Game_status getStatus() {
        return status;
    }

    public void setCell(@NotNull Cell cell) {
        this.cell = cell;
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
    public GameActionEvent(Object source) {
        super(source);
    }
}
