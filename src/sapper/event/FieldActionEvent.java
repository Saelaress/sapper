package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Mine;

public class FieldActionEvent {
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
    public FieldActionEvent(Object source) {
        super();
    }
}
