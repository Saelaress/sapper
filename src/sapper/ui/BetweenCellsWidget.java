package sapper.ui;

import sapper.Orientation;

import javax.swing.*;
import java.awt.*;

public class BetweenCellsWidget extends JPanel {
    private final Orientation orientation;

    public BetweenCellsWidget( Orientation orientation) {
        super(new BorderLayout());
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
        setBackground(Color.BLACK);
    }

    private Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(1, CellWidget.CELL_SIZE) : new Dimension(CellWidget.CELL_SIZE, 1);
    }
}
