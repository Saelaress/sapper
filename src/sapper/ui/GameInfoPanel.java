package sapper.ui;

import sapper.Sapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * Виджет Информационной панели
 */
public class GameInfoPanel extends Box {

    int width;
    private JLabel lifeInfo = new JLabel();
    private JLabel flagInfo = new JLabel();
    Sapper sapper;
    public GameInfoPanel(Sapper sapper, int width) {
        super(0);
        this.sapper = sapper;
        this.width = width;
        setPreferredSize(new Dimension(50, 40));
        add(Box.createHorizontalStrut(10));

        add(new JLabel("Жизни :"));
        add(Box.createHorizontalStrut(10));
        add(lifeInfo);

        add(Box.createHorizontalStrut(20));

        add(new JLabel("Флаги :"));

        add(Box.createHorizontalStrut(10));
        add(flagInfo);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lifeInfo.setText(String.valueOf(sapper.getLife()));
        flagInfo.setText(String.valueOf(sapper.getFlag()));
    }
}
