package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.*;
import sapper.event.FieldActionEvent;
import sapper.event.FieldActionListener;
import test.environment_generator.TestEnvironment_generator;

import java.awt.*;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестов поля
 */
public class FieldTest {
    private int eventCount = 0;

    class FieldObserver implements FieldActionListener {
        @Override
        public void mineIsDetonated(@NotNull FieldActionEvent event) {
            eventCount += 1;
        }

        @Override
        public void cellIsOpen(@NotNull FieldActionEvent event) {
        }
    }

    private Field field;

    @BeforeEach
    public void testSetup() {
        field = new Field(2, 2);
    }

    @Test
    public void test_create_withCorrectParams() {
        Cell cell_0_0 = field.getCell(new Point(0, 0));
        Cell cell_0_1 = field.getCell(new Point(1, 0));
        Cell cell_1_0 = field.getCell(new Point(0, 1));
        Cell cell_1_1 = field.getCell(new Point(1, 1));

        Iterator <Cell> neighborIter = cell_0_0.neighborCells.iterator();
        assertEquals(cell_0_1, neighborIter.next());
        assertEquals(cell_1_0, neighborIter.next());
        assertEquals(cell_1_1, neighborIter.next());

        neighborIter = cell_0_1.neighborCells.iterator();
        assertEquals(cell_0_0, neighborIter.next());
        assertEquals(cell_1_0, neighborIter.next());
        assertEquals(cell_1_1, neighborIter.next());

        neighborIter = cell_1_0.neighborCells.iterator();
        assertEquals(cell_0_0, neighborIter.next());
        assertEquals(cell_0_1, neighborIter.next());
        assertEquals(cell_1_1, neighborIter.next());

        neighborIter = cell_1_1.neighborCells.iterator();
        assertEquals(cell_1_0, neighborIter.next());
        assertEquals(cell_0_1, neighborIter.next());
        assertEquals(cell_0_0, neighborIter.next());
    }

    @Test
    public void test_create_withNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(-1, 1));
    }

    @Test
    public void test_create_withZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(0, 1));
    }

    @Test
    public void test_create_withNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, -1));
    }

    @Test
    public void test_create_withZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, 0));
    }

    @Test
    public void test_openMinedCells() {
        field.getCell(new Point(0,0)).setItem(new Mine());
        field.getCell(new Point(1,1)).setItem(new Mine());

        field.openMinedCells();

        assertEquals(State.OPEN, field.getCell(new Point(0,0)).getState());
        assertEquals(State.CLOSE, field.getCell(new Point(0,1)).getState());
        assertEquals(State.CLOSE, field.getCell(new Point(1,0)).getState());
        assertEquals(State.OPEN, field.getCell(new Point(1,1)).getState());
    }

    @Test
    public void test_openWallCells() {
        Cell cell1 = field.getCell(new Point(0,0));
        Cell cell2 = field.getCell(new Point(1,1));
        Wall fw = new Wall(cell1);
        Wall sw = new Wall(cell2);
        fw.neighborWalls.add(sw);
        sw.neighborWalls.add(fw);

        cell1.setItem(fw);
        cell2.setItem(sw);

        field.getCell(new Point(0,0)).open();

        assertEquals(State.OPEN, field.getCell(new Point(0,0)).getState());
        assertTrue(field.getCell(new Point(0,0)).getItem().isOpened());
        assertEquals(State.CLOSE, field.getCell(new Point(0,1)).getState());
        assertEquals(State.CLOSE, field.getCell(new Point(1,0)).getState());
        assertEquals(State.OPEN, field.getCell(new Point(1,1)).getState());
        assertTrue(field.getCell(new Point(1,1)).getItem().isOpened());

    }

    @Test
    public void test_WallNeighbors() {
        Field newField = new Field(3, 3);
        newField.getCell(new Point(0, 0)).setItem(new Wall(newField.getCell(new Point(0, 0))));
        newField.getCell(new Point(1, 0)).setItem(new Wall(newField.getCell(new Point(1, 0))));
        newField.getCell(new Point(1, 1)).setItem(new Wall(newField.getCell(new Point(1, 1))));
        newField.getCell(new Point(2, 0)).setItem(new Wall(newField.getCell(new Point(2, 0))));
        newField.setupNeighborWalls();

        Cell cell = newField.getCell(new Point(0, 0));
        Wall wall = (Wall) cell.getItem();

        ListIterator<Wall> neighboringWallIt = wall.neighborWalls.listIterator();
        Wall nw = neighboringWallIt.next();
        assertEquals(nw, newField.getCell(new Point(1, 0)).getItem());
        nw = neighboringWallIt.next();
        assertEquals(nw, newField.getCell(new Point(1, 1)).getItem());
        assertThrows(NoSuchElementException.class, () -> neighboringWallIt.next());
    }

    @Test
    public void test_areAllEmptyCellsOpen_true() {
        field.getCell(new Point(0,0)).setItem(new Mine());
        field.getCell(new Point(1,1)).setItem(new Mine());

        field.getCell(new Point(0,1)).open();
        field.getCell(new Point(1,0)).open();

        assertTrue(field.areAllEmptyCellsOpen());
    }

    @Test
    public void test_areAllEmptyCellsOpen_false() {
        field.getCell(new Point(0,0)).setItem(new Mine());
        field.getCell(new Point(1,1)).setItem(new Mine());

        assertFalse(field.areAllEmptyCellsOpen());
    }

    @Test
    public void test_areAllEmptyCellsOpen_false_openOnlyMinedCells() {
        field.getCell(new Point(0,0)).setItem(new Mine());
        field.getCell(new Point(1,1)).setItem(new Mine());

        field.getCell(new Point(0,0)).open();
        field.getCell(new Point(1,1)).open();

        assertFalse(field.areAllEmptyCellsOpen());
    }

    @Test
    public void test_openMinedCellEvent() {
        eventCount = 0;
        Game game = new Game(new TestEnvironment_generator(), new Sapper(3, 3));
        Field newfield = game.getGameField();
        newfield.addFieldlActionListener(new FieldObserver());

        int expectedEventCount = 2;

        newfield.getCell(new Point(0, 2)).open();
        newfield.getCell(new Point(2, 0)).open();

        assertEquals(expectedEventCount, eventCount);
    }
}
