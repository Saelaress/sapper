package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.Cell;
import sapper.Field;
import sapper.event.FieldActionEvent;
import sapper.event.FieldActionListener;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private int eventCount = 0;

    class FieldObserver implements FieldActionListener {
        @Override
        public void mineIsDetonated(@NotNull FieldActionEvent event) {
            eventCount += 1;
        }
    }

    private Field field;

    @BeforeEach
    public void testSetup() {
        eventCount = 0;
        field = new Field(2, 2);
        field.addFieldlActionListener(new FieldObserver());
    }

    @Test
    public void test_create_withCorrectParams() {
        Cell cell_0_0 = field.getCell(new Point(0, 0));
        Cell cell_0_1 = field.getCell(new Point(1, 0));
        Cell cell_1_0 = field.getCell(new Point(0, 1));
        Cell cell_1_1 = field.getCell(new Point(1, 1));

//        assertEquals(Direction.SOUTH, cell_0_0.isNeighbor(cell_1_0));
//        assertEquals(Direction.SOUTH, cell_0_1.isNeighbor(cell_1_1));
//        assertEquals(Direction.NORTH, cell_1_1.isNeighbor(cell_0_1));
//        assertEquals(Direction.NORTH, cell_1_0.isNeighbor(cell_0_0));
//        assertEquals(Direction.EAST, cell_0_0.isNeighbor(cell_0_1));
//        assertEquals(Direction.EAST, cell_1_0.isNeighbor(cell_1_1));
//        assertEquals(Direction.WEST, cell_0_1.isNeighbor(cell_0_0));
//        assertEquals(Direction.WEST, cell_1_1.isNeighbor(cell_1_0));
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
}
