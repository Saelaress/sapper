package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.EnvironmentRandom;
import sapper.environments.EnvironmentSecond;
import sapper.event.GameActionEvent;
import sapper.event.GameActionListener;
import sapper.ui.FieldWidget;
import sapper.ui.GameInfoPanel;
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
        private Sapper sapper;
        private GameWidget gameWidget;

        public GamePanel() throws HeadlessException {
            setTitle("Сапёр");
            setVisible(true);
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("ui/img/mine.png")));
            setIconImage(icon.getImage());

            startGame();

            JPanel content = (JPanel) this.getContentPane();
            FieldWidget fieldWidget = new FieldWidget(game.getGameField());
            GameInfoPanel gameInfoPanel = new GameInfoPanel(sapper, this.getWidth());
            gameWidget = new GameWidget(game, gameInfoPanel, fieldWidget);
            gameWidget.setListenerField();

            content.add(gameInfoPanel, BorderLayout.NORTH);
            content.add(gameWidget);
            content.add(fieldWidget);

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private void startGame(){
            sapper = new Sapper(3, 7);
            EnvironmentRandom env = new EnvironmentRandom();
            env.setFieldParam(7,7);
            game = new Game(env, sapper);

            game.addGameActionListener(new GameController());
        }

        private final class GameController implements GameActionListener {

            @Override
            public void gameStatusChanged(@NotNull GameActionEvent event) {
                gameWidget.getFieldWidget().repaint();
                switch (event.getStatus()){
                    case LOSS:
                        JOptionPane.showMessageDialog(GamePanel.this, "Вы проиграли! ");
                        break;
                    case VICTORY:
                        JOptionPane.showMessageDialog(GamePanel.this, "Вы победили! ");
                        break;
                }
            }

            @Override
            public void mineIsDetonated(@NotNull GameActionEvent event) {}
        }
    }
}
