package sapper.ui;

import sapper.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWidget extends JPanel {
    private final Game game;
    private FieldWidget fieldWidget;

    public GameWidget(Game game){
        this.game = game;

        setFocusable(true);
        requestFocus();
    }

    public void setListenerField(){
        fieldWidget.addMouseListener(new MouseController());
    }

    public FieldWidget getFieldWidget(){
        return fieldWidget;
    }
    public void setFieldWidget(FieldWidget fw){
        this.fieldWidget = fw;
    }

    private class MouseController implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            if(game.status() == Game_status.PLAYED)
            {
                int x = e.getX() / CellWidget.CELL_SIZE;
                int y = e.getY() / CellWidget.CELL_SIZE;
                Cell activeCell = game.getGameField().getCell(new Point(x, y));
//                CellWidget cw = fieldWidget.getCellWidget(game.getGameField().getCell(new Point(x, y)));
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.print("L");
                    game.getSapper().openCell(activeCell);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.print("R");

                    if (activeCell.getState() == State.FLAG) {
                        game.getSapper().removeFlag(activeCell);
                    } else game.getSapper().demine(activeCell);
                }
            }
            if(e.getButton() == MouseEvent.BUTTON2) {
//                startGame();
            }
            fieldWidget.repaint();
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
