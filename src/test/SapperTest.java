package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.Cell;
import sapper.Game;
import sapper.Sapper;
import sapper.State;
import test.environment_generator.TestEnvironment_generator;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SapperTest {
    private Sapper sapper;
    private Game game;

    @BeforeEach
    public void testSetup() {
        game = new Game(new TestEnvironment_generator(), new Sapper(3, 3));
        sapper = game.getSapper();
    }

    @Test
    public void test_demine() {
        Cell minedCell = game.getGameField().getCell(new Point(2,2));
        sapper.demine(minedCell);

        assertEquals(2, sapper.getFlag());
        assertEquals(State.FLAG, minedCell.getState());
    }

    @Test
    public void test_openEmptyCell() {
        Cell emptyCell = game.getGameField().getCell(new Point(0,0));
        sapper.openCell(emptyCell);

        assertEquals(State.OPEN, emptyCell.getState());
        assertEquals(State.OPEN, game.getGameField().getCell(new Point(1,0)).getState());
        assertEquals(1, game.getGameField().getCell(new Point(1,0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, game.getGameField().getCell(new Point(0,1)).getState());
        assertEquals(1, game.getGameField().getCell(new Point(0,1)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, game.getGameField().getCell(new Point(1,1)).getState());
        assertEquals(3, game.getGameField().getCell(new Point(1,1)).getNumOfNeighboringMines());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,1)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,2)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,2)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,2)).getState());
    }

    @Test
    public void test_openCellWithNumber() {
        Cell numberCell = game.getGameField().getCell(new Point(2,1));
        sapper.openCell(numberCell);

        assertEquals(State.OPEN, numberCell.getState());
        assertEquals(2, numberCell.getNumOfNeighboringMines());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,1)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,1)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,2)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,2)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,2)).getState());
    }

    @Test
    public void test_openMinedCell() {
        Cell minedCell = game.getGameField().getCell(new Point(2,2));
        sapper.openCell(minedCell);

        assertEquals(State.OPEN, minedCell.getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,1)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,1)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,0)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(2,1)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(1,2)).getState());
        assertEquals(State.CLOSE, game.getGameField().getCell(new Point(0,2)).getState());
        assertEquals(2, game.getSapper().getLife());
    }

    @Test
    public void test_removeFlagFromCellWithFlag() {
        Cell flagCell = game.getGameField().getCell(new Point(2,2));
        sapper.demine(flagCell);
        assertEquals(2, game.getSapper().getFlag());

        sapper.removeFlag(flagCell);
        assertEquals(3, game.getSapper().getFlag());
    }

    @Test
    public void test_removeFlagFromCellWithoutFlag() {
        Cell flagCell = game.getGameField().getCell(new Point(2,2));

        sapper.removeFlag(flagCell);
        assertEquals(3, game.getSapper().getFlag());
    }
}
