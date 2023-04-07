package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.*;
import sapper.event.FieldActionEvent;
import sapper.event.FieldActionListener;
import sapper.event.MineActionEvent;
import sapper.event.MineActionListener;
import test.environment_generator.TestEnvironment_generator;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;

    private int eventCountMinedDetonated = 0;
    private int eventCountCellIsOpen = 0;

    class FieldObserver implements FieldActionListener {

        @Override
        public void mineIsDetonated(@NotNull FieldActionEvent event) {
            eventCountMinedDetonated += 1;
        }

        @Override
        public void cellIsOpen(@NotNull FieldActionEvent event) {
            eventCountCellIsOpen += 1;
        }
    }

    class MineObserver implements MineActionListener {

        @Override
        public void mineIsDetonated(@NotNull MineActionEvent event) {
            eventCountMinedDetonated += 1;
        }
    }

    @BeforeEach
    public void testSetup() {
        eventCountMinedDetonated = 0;
        eventCountCellIsOpen = 0;
        game = new Game(new TestEnvironment_generator(), new Sapper(3, 3));
        game.getGameField().addFieldlActionListener(new FieldObserver());
    }

    @Test
    public void test_mineDetonated() {
        game.getGameField().getCell(new Point(0,2)).open();
        game.getGameField().getCell(new Point(2,0)).open();

        assertEquals(1, game.getSapper().getLife());
    }

    @Test
    public void test_gameEndedInVictory() {
        game.getGameField().getCell(new Point(0,0)).open();
        game.getGameField().getCell(new Point(2,1)).open();
        game.getGameField().getCell(new Point(1,2)).open();

        assertEquals(3, game.getSapper().getLife());
        assertEquals(0, eventCountMinedDetonated);
        assertEquals(3, eventCountCellIsOpen);
        assertEquals(Game_status.VICTORY, game.status());
    }

    @Test
    public void test_gameEndedInLoss() {
        game.getGameField().getCell(new Point(0,2)).open();
        game.getGameField().getCell(new Point(2,0)).open();
        game.getGameField().getCell(new Point(2,2)).open();

        assertEquals(0, game.getSapper().getLife());
        assertEquals(3, eventCountMinedDetonated);
        assertEquals(3, eventCountCellIsOpen);
        assertEquals(Game_status.LOSS, game.status());
    }

    @Test
    public void test_gameEndedInLoss_ButLeftClosedMinedCell() {
        Mine newMine = new Mine();
        newMine.addMineActionListener(new MineObserver());
        game.getGameField().getCell(new Point(0,0)).setMine(newMine);
        game.getGameField().getCell(new Point(0,2)).open();
        game.getGameField().getCell(new Point(2,0)).open();
        game.getGameField().getCell(new Point(2,2)).open();

        assertEquals(0, game.getSapper().getLife());
        assertEquals(4, eventCountMinedDetonated);
        assertEquals(4, eventCountCellIsOpen);
        assertEquals(Game_status.LOSS, game.status());
        assertEquals(State.OPEN, game.getGameField().getCell(new Point(0,0)).getState());
    }
}
