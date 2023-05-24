package test;

import org.junit.jupiter.api.Test;
import sapper.Cell;
import sapper.Wall;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WallTest {

    @Test
    public void test_open() {
        Wall wall = new Wall(new Cell());

        wall.openItem();

        assertTrue(wall.isOpened());
    }
}