package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sapper.Game;
import sapper.event.GameActionEvent;
import sapper.event.GameActionListener;
import test.environment_generator.TestEnvironment_generator;

public class GameTest {

    private Game game;

    //private enum Event {MINE_DETONATED}

    //private List<Pare<Event, Robot>> events = new ArrayList<>();
    //private List<Pare<Event, Robot>> expectedEvents = new ArrayList<>();

    private class EventListener implements GameActionListener{

        @Override
        public void mineIsDetonated(@NotNull GameActionEvent event) {

        }
    }

    @BeforeEach
    public void testSetup() {

        game = new Game(new TestEnvironment_generator());
        game.addGameActionListener(new EventListener());
    }

    @Test
    public void test_mineDetonated() {



    }

    @Test
    public void test_gameEndedInVictory() {

    }

    @Test
    public void test_gameEndedInLoss() {

    }
}
