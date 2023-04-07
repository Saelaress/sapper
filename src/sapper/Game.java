package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.Environment_generator;
import sapper.event.FieldActionEvent;
import sapper.event.FieldActionListener;

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
        else game_status = Game_status.LOSS;
        return game_status;
    }

    public boolean isPossibleToContinue() {
        if(!gameField.areAllEmptyCellsOpen() && getSapper().getLife() > 0) {
            return true;
        }
        else return false;
    }

    /** Events */

    private class FieldObserver implements FieldActionListener {

        @Override
        public void mineIsDetonated(@NotNull FieldActionEvent event) {
            getSapper().decLife();
        }
    }

//    private ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();
//
//    public void addGameActionListener(@NotNull GameActionListener listener) {
//        gameActionListeners.add(listener);
//    }
//
//    public void removeGameActionListener(@NotNull GameActionListener listener) {
//        gameActionListeners.remove(listener);
//    }

}

