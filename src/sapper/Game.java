package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.Environment_generator;
import sapper.event.FieldActionEvent;
import sapper.event.FieldActionListener;
import sapper.event.GameActionListener;

import java.util.ArrayList;

public class Game {

    private Game_status game_status;
    private Field gameField;

    private Sapper sapper;

    private Environment_generator environment;

    public Game(Environment_generator environment, Sapper sapper) {
        this.environment = environment;

        this.sapper = sapper;
        init();
    }

    public Sapper getSapper() {
        return sapper;
    }

    public Game_status status() {
        return game_status;
    }

    public void init() {

    }

    private void buildField() {
        gameField = environment.buildField();
    }

    public void abort() {

    }

    public Field getGameField() {
        return gameField;
    }

    public Game_status determOutcome() {
        return Game_status.VICTORY;
    }

    public boolean isPossibleToContinue() {
        return false;
    }

    /** Events */

    private class FieldObserver implements FieldActionListener {

        @Override
        public void mineIsDetonated(@NotNull FieldActionEvent event) {

        }
    }

    private ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    public void addGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void removeGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

}

