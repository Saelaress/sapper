package sapper.ui;

import sapper.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWidget extends JPanel {
    private final Game game;
    private FieldWidget fieldWidget;
    private GameInfoPanel gameInfoPanel;

    public GameWidget(Game game, GameInfoPanel gameInfoPanel, FieldWidget fieldWidget){
        this.game = game;
        this.gameInfoPanel = gameInfoPanel;
        this.fieldWidget = fieldWidget;
        setFocusable(true);
        requestFocus();
    }

    public void setListenerField(){
        fieldWidget.addMouseListener(new MouseController());
    }

    public FieldWidget getFieldWidget(){ return fieldWidget; }
    public GameInfoPanel getGameInfoPanel(){
        return gameInfoPanel;
    }

    private class MouseController implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            if(game.status() == Game_status.PLAYED)
            {
                int x = e.getX() / CellWidget.CELL_SIZE;
                int y = e.getY() / CellWidget.CELL_SIZE;
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
            fieldWidget.repaint();
            gameInfoPanel.repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

}
