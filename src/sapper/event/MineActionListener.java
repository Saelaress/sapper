package sapper.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface MineActionListener extends EventListener {
    public void mineIsDetonated(@NotNull MineActionEvent event);
}
