package sapper.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface GameActionListener extends EventListener {

    public void cellIsOpen(@NotNull GameActionEvent event);

    void gameStatusChanged(@NotNull GameActionEvent event);
}
