package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.EnvironmentSecond;
import sapper.event.GameActionEvent;
import sapper.event.GameActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainWindow extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private Game game;

        private JPanel fieldPanel = new JPanel();

        private JPanel infoPanel = new JPanel();
        private JLabel lifeInfo = new JLabel();
        private JLabel flagInfo = new JLabel();
        private JLabel timeInfo = new JLabel();
        private final int CELL_SIZE = 50;

        private JMenuBar menu = null;
        private final String fileItems[] = new String []{"Новая игра", "Выход"};

        public GamePanel() throws HeadlessException {
            this.setTitle("Сапёр");

            // Представление должно реагировать на изменение состояния модели
            game = new Game(new EnvironmentSecond(), new Sapper(2, 3));
            game.addGameActionListener(new GameController());

            // Меню
            createMenu();
            setJMenuBar(menu);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Box mainBox = Box.createVerticalBox();

            // Информационная панель
            mainBox.add(Box.createVerticalStrut(10));
            mainBox.add(createInfoPanel());

            // Игровое поле
            mainBox.add(Box.createVerticalStrut(10));
            fieldPanel.setDoubleBuffered(true);
            createField();
            setEnabledField(true);
            mainBox.add(fieldPanel);

            setContentPane(mainBox);
            pack();
            setResizable(false);
            setVisible(true);
        }

        // ----------------------------- Создаем меню ----------------------------------

        private void createMenu() {

            menu = new JMenuBar();
            JMenu fileMenu = new JMenu("Игра");

            for (int i = 0; i < fileItems.length; i++) {

                JMenuItem item = new JMenuItem(fileItems[i]);
                item.setActionCommand(fileItems[i].toLowerCase());
                item.addActionListener(new NewMenuListener());
                fileMenu.add(item);
            }
            fileMenu.insertSeparator(1);
            menu.add(fileMenu);
        }

        public class NewMenuListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("выход".equals(command)) {
                    System.exit(0);
                }
                if ("новая игра".equals(command)) {
                    game = new Game(new EnvironmentSecond(), new Sapper(2, 3));
                    game.addGameActionListener(new GameController());
                    lifeInfo.setText(String.valueOf(game.getSapper().getLife()));
                    flagInfo.setText(String.valueOf(game.getSapper().getFlag()));
                    timeInfo.setText("?");
                    createField();
                }
            }
        }

        // ---------------------- Создаем информационную панель -----------------------

        private Box createInfoPanel() {

            Box box = Box.createHorizontalBox();

            box.add(Box.createHorizontalStrut(10));

            box.add(new JLabel("Жизни :"));
            lifeInfo.setText(String.valueOf(game.getSapper().getLife()));
            box.add(Box.createHorizontalStrut(10));
            box.add(lifeInfo);

            box.add(Box.createHorizontalStrut(20));

            box.add(new JLabel("Флаги :"));
            flagInfo.setText(String.valueOf(game.getSapper().getFlag()));
            box.add(Box.createHorizontalStrut(10));
            box.add(flagInfo);

            box.add(Box.createHorizontalStrut(20));

            box.add(new JLabel("Время :"));
            timeInfo.setText("?");
            box.add(Box.createHorizontalStrut(10));
            box.add(timeInfo);

            box.add(Box.createHorizontalStrut(20));

            return box;
        }

        // --------------------------- Отрисовываем поле ------------------------------

        private void createField(){

            fieldPanel.setDoubleBuffered(true);
            fieldPanel.setLayout(new GridLayout(game.getGameField().getHeight(), game.getGameField().getWidth()));

            Dimension fieldDimension = new Dimension(CELL_SIZE*game.getGameField().getHeight(), CELL_SIZE*game.getGameField().getWidth());

            fieldPanel.setPreferredSize(fieldDimension);
            fieldPanel.setMinimumSize(fieldDimension);
            fieldPanel.setMaximumSize(fieldDimension);

            //Отрисовываем поле
            fieldPanel.removeAll();

            for (int row = 1; row <= game.getGameField().getHeight(); row++)
            {
                for (int col = 1; col <= game.getGameField().getWidth(); col++)
                {
                    JButton button = new JButton("");
                    button.setFocusable(false);
                    fieldPanel.add(button);
                    button.addMouseListener(new mouseController());
                }
            }

            fieldPanel.validate();
        }

        public void repaintField() {
        //TODO
            lifeInfo.setText(String.valueOf(game.getSapper().getLife()));
            flagInfo.setText(String.valueOf(game.getSapper().getFlag()));

            for (int row = 0; row < game.getGameField().getHeight(); row++)
            {
                for (int col = 0; col < game.getGameField().getWidth(); col++)
                {
                    JButton btn = getButton(new Point(row+1, col+1));

                    Cell cell = game.getGameField().getCell(new Point(row, col));
                    State state = cell.getState();

                    if(state == State.OPEN){
                        btn.setEnabled(false);
                        int numOfNeighboringMines = cell.getNumOfNeighboringMines();
                        if(numOfNeighboringMines > 0) {
                            btn.setText(String.valueOf(numOfNeighboringMines));
                            if(btn.getMouseListeners().length > 1) {
                                btn.removeMouseListener(btn.getMouseListeners()[1]);
                            }
                        }
                    }

                    if(state == State.FLAG){
                        btn.setText("FLAG");
                    }

                    if(state == State.CLOSE){
                        btn.setText("");
                    }
                }
            }
        }

        private Point buttonPosition(JButton btn){

            int index = 0;
            for(Component widget: fieldPanel.getComponents())
            {
                if(widget instanceof JButton)
                {
                    if(btn.equals((JButton)widget))
                    {
                        break;
                    }

                    index++;
                }
            }

            int fieldWidth = game.getGameField().getWidth();
            return new Point(index%fieldWidth, index/fieldWidth);
        }

        private JButton getButton(Point pos) {

            int index = game.getGameField().getWidth()*(pos.y-1) + (pos.x-1);

            for(Component widget: fieldPanel.getComponents())
            {
                if(widget instanceof JButton)
                {
                    if(index == 0)
                    {
                        return (JButton)widget;
                    }
                    index--;
                }
            }

            return null;
        }

        private void setEnabledField(boolean on){

            Component comp[] = fieldPanel.getComponents();
            for(Component c : comp)
            {    c.setEnabled(on);   }
        }

        // ------------------------- Реагируем на действия игрока ----------------------

        private class mouseController implements MouseListener {
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    JButton button = (JButton) e.getSource();
                    Point p = buttonPosition(button);
                    if(game.getGameField().getCell(p).getState() != State.FLAG) {
                        button.setEnabled(false);

                        // Открываем выбранную ячейку
                        game.getSapper().openCell(game.getGameField().getCell(p));
                        button.removeMouseListener(button.getMouseListeners()[1]);
                        repaintField();
                    }
                }

                if(e.getButton() == MouseEvent.BUTTON3) {
                    JButton button = (JButton) e.getSource();
                    Point p = buttonPosition(button);
                    Cell cell = game.getGameField().getCell(p);
                    //Если в ячейке есть флаг, убираем
                    if(cell.getState() == State.FLAG) {
                        game.getSapper().removeFlag(cell);
                    }
                    //Иначе устанавливаем в выбранную ячейку флаг
                    else {
                        game.getSapper().demine(cell);
                    }
                    repaintField();
                }
            }
        }

            private final class GameController implements GameActionListener {
            @Override
            public void cellIsOpen(@NotNull GameActionEvent event) {
                for (int row = 0; row < game.getGameField().getHeight(); row++)
                {
                    for (int col = 0; col < game.getGameField().getWidth(); col++) {
                        if(game.getGameField().getCell(new Point(row, col)).equals(event.getCell())) {
                            JButton btn = getButton(new Point(row+1, col+1));
                            btn.setEnabled(false);

                            //Если ячейка заминирована
                            if(event.getMined()) {
                                btn.setText("*");
                            }
                        }
                    }
                }
            }

            @Override
            public void gameStatusChanged(@NotNull GameActionEvent event) {
                Game_status status = event.getStatus();
                switch (status) {
                    case VICTORY:
                        JOptionPane.showMessageDialog(GamePanel.this, "Игра завершена победой");
                        break;
                    case GAME_ABORTED:
                        JOptionPane.showMessageDialog(GamePanel.this, "Игра завершена досрочно");
                        break;
                    case LOSS:
                        JOptionPane.showMessageDialog(GamePanel.this, "Игра завершена проигрышем");
                        break;
                }
            }
        }
    }
}