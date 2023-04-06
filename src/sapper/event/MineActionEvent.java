package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Mine;

import java.util.EventObject;

public class MineActionEvent extends EventObject {
    private Mine mine;

    public void setMine(@NotNull Mine mine) {
        this.mine = mine;
    }

    public Mine getMine() {
        return mine;
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
