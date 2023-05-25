package test;

import org.junit.jupiter.api.Test;
import sapper.Mine;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс тестов мины
 */
public class MineTest {

    @Test
    public void test_detonate() {
        Mine mine = new Mine();

        mine.openItem();

        assertTrue(mine.isOpened());
    }
}
