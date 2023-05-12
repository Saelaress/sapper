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
        game_status = Game_status.PLAYED;
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
        if (game_status != Game_status.VICTORY && game_status != Game_status.LOSS) {
            if(getSapper().getLife() > 0) {
                game_status = Game_status.VICTORY;
            }
            else {
                game_status = Game_status.LOSS;
                getGameField().openMinedCells();
            }
            fireGameStatusChanged(game_status);
        }
        return game_status;
    }

    public boolean isPossibleToContinue() {
        if(!gameField.areAllEmptyCellsOpen() && getSapper().getLife() > 0) {
            return true;
        }
        else return false;
    }

    /** Events */

    public void fireGameStatusChanged(Game_status status) {
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
            fireMineIsDetonated(event.getMine());
        }

        private void fireMineIsDetonated(Mine mine) {
            for(GameActionListener listener: gameListListener) {
                GameActionEvent event = new GameActionEvent(listener);
                event.setMine(mine);
                listener.mineIsDetonated(event);
            }
        }

        @Override
        public void cellIsOpen(@NotNull FieldActionEvent event) {
            if(!isPossibleToContinue()) {
                determOutcome();
            }
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

