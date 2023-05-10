package sapper;

import org.jetbrains.annotations.NotNull;
import sapper.environments.EnvironmentSecond;
import sapper.event.GameActionEvent;
import sapper.event.GameActionListener;
import sapper.ui.GameInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GamePanel extends JFrame {
    private Game game;

    private JPanel fieldWidget;
    private JLabel label;
    private final int CELL_SIZE = 60;

    public static void main(String[] args) {
        new GamePanel();
    }

    private GamePanel(){
        startGame();
//        setImages();
        initLabel();
        add(new GameInfoPanel(game.getSapper(), game.getGameField().getWidth()*CELL_SIZE));
        initPanel();
        initFrame();
    }

    private void startGame(){
        Sapper sapper = new Sapper(2, 3);
        game = new Game(new EnvironmentSecond(), sapper);
        game.addGameActionListener(new GameObserver());
//        EnvironmentRandom environmentRandom = new EnvironmentRandom();
//        int height = 5;
//        int width = 7;
//        environmentRandom.setFieldParam(height, width);
//        environmentRandom.buildField();
//        int countMine = Math.min(height, width);
//        game = new Game(environmentRandom, new Sapper(countMine, Math.min(1, countMine-3)));
//
//        game.addGameActionListener(new GameObserver());
    }

    private void initLabel(){
        label = new JLabel("Начните игру!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel(){
        fieldWidget = new JPanel(){

            private Image createFlag(boolean isLight) {
                BufferedImage bufferedImage = new BufferedImage(CELL_SIZE,CELL_SIZE,BufferedImage.TYPE_INT_ARGB);
                Graphics g = bufferedImage.getGraphics();

                if(isLight) {
                    g.drawImage(getImage("cell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
                }
                else g.drawImage(getImage("cell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
                g.drawImage(getImage("flag"), CELL_SIZE/10, CELL_SIZE/10,  CELL_SIZE*8/10, CELL_SIZE*8/10,null);

                return bufferedImage;
            }

            private Image createMine(boolean isLight) {
                BufferedImage bufferedImage = new BufferedImage(CELL_SIZE,CELL_SIZE,BufferedImage.TYPE_INT_ARGB);
                Graphics g = bufferedImage.getGraphics();

                if(isLight) {
                    g.drawImage(getImage("openCell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
                }
                else g.drawImage(getImage("openCell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
                g.drawImage(getImage("mine"), CELL_SIZE/10, CELL_SIZE/10, CELL_SIZE*8/10, CELL_SIZE*8/10, null);

                return bufferedImage;
            }

            private Image createNum(boolean isLight, int num) {
                BufferedImage bufferedImage = new BufferedImage(CELL_SIZE,CELL_SIZE,BufferedImage.TYPE_INT_ARGB);
                Graphics g = bufferedImage.getGraphics();

                if(isLight) {
                    g.drawImage(getImage("openCell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
                }
                else g.drawImage(getImage("openCell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
                g.drawImage(getImage("num" + num), CELL_SIZE/3, CELL_SIZE/5, getImage("num" + num).getWidth(this), CELL_SIZE*4/10, null);

                return bufferedImage;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = 0; row < game.getGameField().getHeight(); row++) {
                    for (int col = 0; col < game.getGameField().getWidth(); col++) {
                        Cell cell = game.getGameField().getCell(new Point(row, col));
                        if(cell.getState() == State.CLOSE) {
                            if(isLightCell(row, col)) {
                                g.drawImage(getImage("cell1"), row * CELL_SIZE,
                                        col * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
                            }
                            else {
                                g.drawImage(getImage("cell2"), row * CELL_SIZE,
                                        col * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
                            }
                        }
                        if(cell.getState() == State.OPEN) {
                            if(cell.isMined()) {
                                g.drawImage(createMine(isLightCell(row, col)), row * CELL_SIZE,
                                        col * CELL_SIZE,  CELL_SIZE, CELL_SIZE,null);
                            }
                            else {
                                if(cell.getNumOfNeighboringMines()>0){
                                    g.drawImage(createNum(isLightCell(row, col), cell.getNumOfNeighboringMines()), row * CELL_SIZE,
                                            col * CELL_SIZE,  CELL_SIZE, CELL_SIZE,this);
                                }
                                else {
                                    if (isLightCell(row, col)) {
                                        g.drawImage(getImage("openCell1"), row * CELL_SIZE,
                                                col * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
                                    } else {
                                        g.drawImage(getImage("openCell2"), row * CELL_SIZE,
                                                col * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
                                    }
                                }
                            }
                        }
                        if(cell.getState() == State.FLAG) {
                            g.drawImage(createFlag(isLightCell(row, col)), row * CELL_SIZE,
                                    col * CELL_SIZE,  CELL_SIZE, CELL_SIZE,null);
                        }
                    }
                }
            }
        };

        fieldWidget.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(game.status() == Game_status.PLAYED)
                {
                    int x = e.getX() / CELL_SIZE;
                    int y = e.getY() / CELL_SIZE;
                    Cell activeCell = game.getGameField().getCell(new Point(x, y));
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        game.getSapper().openCell(activeCell);
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (activeCell.getState() == State.FLAG) {
                            game.getSapper().removeFlag(activeCell);
                        } else game.getSapper().demine(activeCell);
                    }
                }
                if(e.getButton() == MouseEvent.BUTTON2) {
                    startGame();
                }
                label.setText(getMessage());
                fieldWidget.repaint();
            }
        }
        );

        fieldWidget.setPreferredSize(new Dimension(
                game.getGameField().getWidth() * CELL_SIZE,
                game.getGameField().getHeight() * CELL_SIZE));
        add(fieldWidget);
    }

    private boolean isLightCell(int row, int col) {
        return (row % 2 == 0 & col % 2 == 0) || (row % 2 != 0 & col % 2 != 0);
    }

    private String getMessage() {
        switch (game.status()){
            case PLAYED: return "Идет игра...";
            case LOSS:
                fieldWidget.setEnabled(false);
                return "Игра завершена проигрышем.";
            case VICTORY:
                fieldWidget.setEnabled(false);
                return "Игра завершена победой!";
            default: return "Начните игру!";
        }
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сапёр");

        setResizable(true);
        setVisible(true);
        setIconImage(getImage("mine"));
        pack();
        setLocationRelativeTo(null);
    }

    private Image getImage(String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        return icon.getImage();
    }

    private class GameObserver implements GameActionListener{
        @Override
        public void mineIsDetonated(@NotNull GameActionEvent event) {
            fieldWidget.repaint();
        }
    }
}