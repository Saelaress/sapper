package sapper.event;

import org.jetbrains.annotations.NotNull;
import sapper.Sapper;

import java.util.EventObject;

public class GameActionEvent extends EventObject {

    private Sapper sapper;

    public void setSapper(@NotNull Sapper sapper) { this.sapper = sapper; }

    public Sapper getSapper() {
        return sapper;
    }

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
