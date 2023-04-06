package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.Cell;
import sapper.Field;
import sapper.Mine;
import sapper.State;

import java.awt.*;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;
    private Field field;

    @BeforeEach
    public void testSetup() {
        field = new Field(3, 3);
        cell = field.getCell(new Point(0, 0));
    }

    @Test
    public void test_setMine() {
        Mine mine = new Mine();
        cell.setMine(mine);

        assertEquals(mine, cell.getMine());
    }

    @Test
    public void test_setFlag() {
        cell.setFlag();
        State exp = State.FLAG;
        State res = cell.getState();

        assertEquals(exp, res);
    }

    @Test
    public void test_removeFlag() {
        cell.setFlag();
        assertEquals(State.FLAG, cell.getState());

        assertTrue(cell.removeFlag());
        assertEquals(State.CLOSE, cell.getState());
    }

    @Test
    public void test_openFlagCell() {
        cell.setFlag();
        cell.open();

        assertEquals(State.FLAG, cell.getState());
    }

    @Test
    public void test_openOpenedCell() {
        cell.open();

        assertEquals(State.OPEN, cell.getState());
    }

    @Test
    public void test_openMinedCell() {
        Mine mine = new Mine();
        cell.setMine(mine);
        cell.open();

        assertTrue(mine.isDetonated());
        assertEquals(State.OPEN, cell.getState());
    }

    @Test
    public void test_openCellWithNumber() {
        Iterator <Cell> neighborCellIt= cell.neighborCells.iterator();
        while(neighborCellIt.hasNext()){
            neighborCellIt.next().setMine(new Mine());
        }
        cell.open();
        int numOfNeighboringMines = cell.getNumOfNeighboringMines();
        assertEquals(3, numOfNeighboringMines);
    }

    @Test
    public void test_openEmptyCell() {
        Cell minedCell = field.getCell(new Point(2,2));
        minedCell.setMine(new Mine());
        cell.open();

        //Пустые ячейки
        assertEquals(State.OPEN, field.getCell(new Point(0, 0)).getState());
        assertEquals(0, field.getCell(new Point(0, 0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, field.getCell(new Point(1, 0)).getState());
        assertEquals(0, field.getCell(new Point(1, 0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, field.getCell(new Point(2, 0)).getState());
        assertEquals(0, field.getCell(new Point(2, 0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, field.getCell(new Point(0, 1)).getState());
        assertEquals(0, field.getCell(new Point(0, 1)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, field.getCell(new Point(0, 2)).getState());
        assertEquals(0, field.getCell(new Point(0, 2)).getNumOfNeighboringMines());

        //Ячейки с цифрами
        assertEquals(State.OPEN, field.getCell(new Point(1, 1)).getState());
        assertEquals(1, field.getCell(new Point(1, 1)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, field.getCell(new Point(2, 1)).getState());
        assertEquals(1, field.getCell(new Point(2, 1)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, field.getCell(new Point(1, 2)).getState());
        assertEquals(1, field.getCell(new Point(1, 2)).getNumOfNeighboringMines());

        //Заминироваанная ячейка
        assertEquals(State.CLOSE, minedCell.getState());
    }

    @Test
    public void test_openIfEmpty_butCellIsMined() {
        cell.setMine(new Mine());

        assertFalse(cell.openIfEmpty());
        assertEquals(State.CLOSE, cell.getState());
    }

    @Test
    public void test_openIfEmpty_butCellWithFlag() {
        cell.setFlag();

        assertFalse(cell.openIfEmpty());
        assertEquals(State.FLAG, cell.getState());
    }

    @Test
    public void test_openIfEmpty_EmptyCell() {
        assertTrue(cell.openIfEmpty());
        assertEquals(State.OPEN, cell.getState());
    }

    @Test
    public void test_openEmptyCell_2() {
        Field bigField = new Field(4, 4);
        Cell minedCell1 = bigField.getCell(new Point(1,3));
        Cell minedCell2 = bigField.getCell(new Point(2,2));
        Cell minedCell3 = bigField.getCell(new Point(3,1));
        minedCell1.setMine(new Mine());
        minedCell2.setMine(new Mine());
        minedCell3.setMine(new Mine());
        Cell newCell = bigField.getCell(new Point(0,0));
        newCell.open();

        //Пустые ячейки
        assertEquals(State.OPEN, bigField.getCell(new Point(0, 0)).getState());
        assertEquals(0, bigField.getCell(new Point(0, 0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, bigField.getCell(new Point(1, 0)).getState());
        assertEquals(0, bigField.getCell(new Point(1, 0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, bigField.getCell(new Point(0, 1)).getState());
        assertEquals(0, bigField.getCell(new Point(0, 1)).getNumOfNeighboringMines());

        //Ячейки с цифрами
        assertEquals(State.OPEN, bigField.getCell(new Point(0, 2)).getState());
        assertEquals(1, bigField.getCell(new Point(0, 2)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, bigField.getCell(new Point(2, 0)).getState());
        assertEquals(1, bigField.getCell(new Point(2, 0)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, bigField.getCell(new Point(1, 1)).getState());
        assertEquals(1, bigField.getCell(new Point(1, 1)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, bigField.getCell(new Point(1, 2)).getState());
        assertEquals(2, bigField.getCell(new Point(1, 2)).getNumOfNeighboringMines());
        assertEquals(State.OPEN, bigField.getCell(new Point(2, 1)).getState());
        assertEquals(2, bigField.getCell(new Point(2, 1)).getNumOfNeighboringMines());

        //Заминироваанные ячейки
        assertEquals(State.CLOSE, minedCell1.getState());
        assertEquals(State.CLOSE, minedCell2.getState());
        assertEquals(State.CLOSE, minedCell3.getState());

        //Пустые закрытые ячейки
        assertEquals(State.CLOSE, bigField.getCell(new Point(0, 3)).getState());
        assertEquals(State.CLOSE, bigField.getCell(new Point(3, 0)).getState());
        assertEquals(State.CLOSE, bigField.getCell(new Point(3, 2)).getState());
        assertEquals(State.CLOSE, bigField.getCell(new Point(3, 3)).getState());
        assertEquals(State.CLOSE, bigField.getCell(new Point(2, 3)).getState());
    }
}
