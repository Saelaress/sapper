package sapper.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface FieldActionListener extends EventListener {
    public void mineIsDetonated(@NotNull FieldActionEvent event);
}