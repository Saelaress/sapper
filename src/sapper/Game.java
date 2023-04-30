package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.Environment_generator;
import sapper.event.*;

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
        buildField();

        // Subscribe on field
        gameField.addFieldlActionListener(new FieldObserver());
    }

    private void buildField() {
        gameField = environment.buildField();
        //Subscribe on mines
        gameField.subscribeOnMines();
    }

    public Field getGameField() {
        return gameField;
    }

    public Game_status determOutcome() {
        if(getSapper().getLife() > 0) {
            game_status = Game_status.VICTORY;
        }
        else {
            game_status = Game_status.LOSS;
            getGameField().openMinedCells();
        }
        fireGameStatusChanged(game_status);
        return game_status;
    }

    public boolean isPossibleToContinue() {
        if(!gameField.areAllEmptyCellsOpen() && getSapper().getLife() > 0) {
            return true;
        }
        else return false;
    }

    /** Events */
    private void fireGameStatusChanged(Game_status status) {
        for(GameActionListener listener: gameListListener) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setStatus(status);
            listener.gameStatusChanged(event);
        }
    }

    private class FieldObserver implements FieldActionListener {

        @Override
        public void mineIsDetonated(@NotNull FieldActionEvent event) {
            getSapper().decLife();
        }

        @Override
        public void cellIsOpen(@NotNull FieldActionEvent event) {
            if(!isPossibleToContinue()) {
                determOutcome();
            }
            fireCellIsOpen(event.getCell(), event.getMined());
        }
    }

    private void fireCellIsOpen(Cell cell, boolean isMined) {
        for(GameActionListener listener: gameListListener) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setCell(cell);
            event.setMined(isMined);
            listener.cellIsOpen(event);
        }
    }

    private ArrayList<GameActionListener> gameListListener = new ArrayList<>();

    public void addGameActionListener(GameActionListener listener) {
        gameListListener.add(listener);
    }

    public void removeGameActionListener(GameActionListener listener) {
        gameListListener.remove(listener);
    }
}

