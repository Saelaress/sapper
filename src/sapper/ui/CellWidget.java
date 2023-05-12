package sapper.ui;
import sapper.Cell;
import sapper.State;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class CellWidget extends JPanel {
    public static final int CELL_SIZE = 60;
    Cell cell;
    boolean isLight;

    public CellWidget(Cell cell, boolean isLight) {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        this.cell = cell;
        this.isLight = isLight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(cell.getState() == State.OPEN) {
            if (cell.isMined()) {
                g.drawImage(createMineImage(), 0, 0,  CELL_SIZE, CELL_SIZE,null);
            } else if (cell.getNumOfNeighboringMines() > 0) {
                g.drawImage(createNumImage(cell.getNumOfNeighboringMines()), 0, 0,  CELL_SIZE, CELL_SIZE,null);
            } else {
                if (isLight) {
                    g.drawImage(getImage("openCell1"), 0, 0, CELL_SIZE, CELL_SIZE, null);
                } else g.drawImage(getImage("openCell2"), 0, 0, CELL_SIZE, CELL_SIZE, null);
            }
        }
        else if (cell.getState() == State.CLOSE) {
            if(isLight) {
                g.drawImage(getImage("cell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
            }
            else g.drawImage(getImage("cell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        }
        else if(cell.getState() == State.FLAG) {
            g.drawImage(createFlagImage(), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        }
    }

    private Image createFlagImage() {
        BufferedImage bufferedImage = new BufferedImage(CELL_SIZE,CELL_SIZE,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();

        if(isLight) {
            g.drawImage(getImage("cell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        }
        else g.drawImage(getImage("cell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        g.drawImage(getImage("flag"), CELL_SIZE/10, CELL_SIZE/10,  CELL_SIZE*8/10, CELL_SIZE*8/10,null);

        return bufferedImage;
    }

    private Image createMineImage() {
        BufferedImage bufferedImage = new BufferedImage(CELL_SIZE,CELL_SIZE,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();

        if(isLight) {
            g.drawImage(getImage("openCell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        }
        else g.drawImage(getImage("openCell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        g.drawImage(getImage("mine"), CELL_SIZE/10, CELL_SIZE/10,  CELL_SIZE*8/10, CELL_SIZE*8/10,null);

        return bufferedImage;
    }

    private Image createNumImage(int num) {
        BufferedImage bufferedImage = new BufferedImage(CELL_SIZE,CELL_SIZE,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();

        if(isLight) {
            g.drawImage(getImage("openCell1"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        }
        else g.drawImage(getImage("openCell2"), 0, 0,  CELL_SIZE, CELL_SIZE,null);
        g.drawImage(getImage("num" + num), CELL_SIZE/3, CELL_SIZE/5, getImage("num" + num).getWidth(this), CELL_SIZE*4/10, null);

        return bufferedImage;
    }

    private Image getImage(String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        return icon.getImage();
    }

    public int getCellSize() {
        return CELL_SIZE;
    }
}
