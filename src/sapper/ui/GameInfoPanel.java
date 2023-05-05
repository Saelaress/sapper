package sapper.ui;

import sapper.Sapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class GameInfoPanel extends JPanel {

    Sapper sapper;
    public GameInfoPanel(Sapper sapper, int width) {
        this.sapper = sapper;
        setPreferredSize(new Dimension(width, 50));
        addMouseListener(new MouseController());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Box box = Box.createHorizontalBox();

        box.add(Box.createHorizontalStrut(10));

        g.drawImage(getImage("life"), 0, 0, null);
        box.add(Box.createHorizontalStrut(10));
        g.drawImage(getImage("num" + sapper.getLife()), 0, 0, null);
    }

    private Image getImage(String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        return icon.getImage();
    }

    private class MouseController implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
