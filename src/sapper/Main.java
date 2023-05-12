package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.EnvironmentSecond;
import sapper.event.GameActionEvent;
import sapper.event.GameActionListener;
import sapper.ui.FieldWidget;
import sapper.ui.GameWidget;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {
        private Game game;

        private JLabel label;

        private GameWidget gameWidget;
        public GamePanel() throws HeadlessException {
            setTitle("Сапёр");
            setVisible(true);
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("ui/img/mine.png")));
            setIconImage(icon.getImage());

            Sapper sapper = new Sapper(2, 3);
            game = new Game(new EnvironmentSecond(), sapper);
            gameWidget = new GameWidget(game);

            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            FieldWidget fieldWidget = new FieldWidget(game.getGameField());
            gameWidget.setFieldWidget(fieldWidget);

            gameWidget.setListenerField();
            content.add(gameWidget);
            content.add(fieldWidget);

            initLabel();

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private void initLabel(){
            label = new JLabel("Начните игру!");
            add(label, BorderLayout.SOUTH);
        }

        private final class GameController implements GameActionListener {

            @Override
            public void mineIsDetonated(@NotNull GameActionEvent event) {}
        }
    }
}
