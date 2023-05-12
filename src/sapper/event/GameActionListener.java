package sapper.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface GameActionListener extends EventListener {

    public void gameStatusChanged(@NotNull GameActionEvent event);
    public void mineIsDetonated(@NotNull GameActionEvent event);
}
