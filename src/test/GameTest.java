package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.*;
import sapper.event.GameActionEvent;
import sapper.event.GameActionListener;
import test.environment_generator.TestEnvironment_generator;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;

    private int eventCount = 0;

    class EventListener implements GameActionListener {
        @Override
        public void mineIsDetonated(@NotNull GameActionEvent event) {
            eventCount += 1;
        }
    }

    @BeforeEach
    public void testSetup() {
        eventCount = 0;
        game = new Game(new TestEnvironment_generator(), new Sapper(3, 3));
    }

    @Test
    public void test_mineDetonated() {
        game.getGameField().getCell(new Point(0,2)).open();
        game.getGameField().getCell(new Point(2,0)).open();

        assertEquals(1, game.getSapper().getLife());
        assertEquals(2, eventCount);
    }

    @Test
    public void test_gameEndedInVictory() {
        game.getGameField().getCell(new Point(0,0)).open();
        game.getGameField().getCell(new Point(1,0)).open();
        game.getGameField().getCell(new Point(0,1)).open();
        game.getGameField().getCell(new Point(1,1)).open();
        game.getGameField().getCell(new Point(2,1)).open();
        game.getGameField().getCell(new Point(1,2)).open();

        assertEquals(3, game.getSapper().getLife());
        assertEquals(0, eventCount);
        assertEquals(Game_status.VICTORY, game.status());
    }

    @Test
    public void test_gameEndedInLoss() {
        game.getGameField().getCell(new Point(0,2)).open();
        game.getGameField().getCell(new Point(2,0)).open();
        game.getGameField().getCell(new Point(2,2)).open();

        assertEquals(0, game.getSapper().getLife());
        assertEquals(3, eventCount);
        assertEquals(Game_status.LOSS, game.status());
    }

    @Test
    public void test_gameEndedInLoss_ButLeftClosedMinedCell() {
        game.getGameField().getCell(new Point(0,2)).open();
        game.getGameField().getCell(new Point(2,0)).open();
        game.getGameField().getCell(new Point(2,2)).open();
        game.getGameField().getCell(new Point(0,0)).setMine(new Mine());

        assertEquals(0, game.getSapper().getLife());
        assertEquals(3, eventCount);
        assertEquals(Game_status.LOSS, game.status());
        assertEquals(State.OPEN, game.getGameField().getCell(new Point(0,0)).getState());
    }
}
