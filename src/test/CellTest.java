package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.Cell;
import sapper.Mine;
import sapper.State;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {

    private Cell cell;

    public CellTest() {
    }

    @BeforeEach
    public void testSetup() {
        cell = new Cell();
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

        cell.removeFlag();

        assertEquals(State.CLOSE, cell.getState());
    }

    @Test
    public void test_openFlagCell() {

    }

    @Test
    public void test_openOpenedCell() {

    }

    @Test
    public void test_openMinedCell() {

    }

    @Test
    public void test_openCellWithNumber() {

    }

    @Test
    public void test_openEmptyCell() {

    }

    @Test
    public void test_openIfEmpty_butMinedCell() {

    }

    @Test
    public void test_openIfEmpty_butCellWithNumber() {

    }

    @Test
    public void test_openIfEmpty_EmptyCell() {

    }
}
