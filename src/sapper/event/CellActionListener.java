package sapper.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface CellActionListener extends EventListener {
    public void cellIsOpen(@NotNull CellActionEvent event);
}
