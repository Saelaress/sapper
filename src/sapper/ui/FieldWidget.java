package sapper.ui;

import sapper.Cell;
import sapper.Field;
import sapper.Orientation;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FieldWidget extends JPanel {

    private final Field field;
    private  Map<Cell, CellWidget> cellsWidgets = new HashMap<>();

    public FieldWidget(Field field){
        this.field = field;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
    }

//    public void addBallWidget(Ball b, BallWidget bw){
//        this.ballWidgets.put(b, bw);
//    }
//
//    public BallWidget getBallWidget(Ball b){
//        return ballWidgets.get(b);
//    }
//
//    public void dellBallWidget (Ball b){
//        ballWidgets.remove(b);
//    }
//
    public CellWidget getCellWidget(Cell c){
        return cellsWidgets.get(c);
    }

    public Cell getCell(CellWidget cw){
        for(Cell cell : cellsWidgets.keySet()) {
            if(cellsWidgets.get(cell).equals(cw)) {
                return cell;
            }
        }
        return null;
    }

    private void fillField(){
        for(int i=0; i< field.getHeight(); i++){
            JPanel row =createRow(i);
            add(row);
//            JPanel rowWalls = createRowWalls(i, Direction.SOUTH);
//            add(rowWalls);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Cell cell = field.getCell(new Point(i, rowIndex));

            CellWidget cellWidget = new CellWidget(cell, isLightCell(i, rowIndex));

//            if(cell.getUnit() instanceof Ball){
//                BallWidget ballWidget = new BallWidget((Ball) cell.getUnit());
//                addBallWidget((Ball)cell.getUnit(), ballWidget);
//                cellWidget.addItem(ballWidget);
//            }else if (cell.getUnit() instanceof Gate){
//                GateWidget gateWidget = new GateWidget((Gate) cell.getUnit());
//                cellWidget.setBackground(gateWidget.getBackground());
//            }else if (cell.getUnit() instanceof Wall){
//                cellWidget.setBackground(Color.decode(((Wall) cell.getUnit()).getColor()));
//            }

            cellsWidgets.put(cell, cellWidget);

//            BetweenCellsWidget westCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
//            row.add(westCellWidget);

            row.add(cellWidget);

//            BetweenCellsWidget eastCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
//            row.add(eastCellWidget);
        }
        return row;
    }

    private boolean isLightCell(int row, int col) {
        return (row % 2 == 0 & col % 2 == 0) || (row % 2 != 0 & col % 2 != 0);
    }

//    private JPanel createRowWalls(int rowIndex, Direction direction) {
//        if(direction == Direction.EAST || direction == Direction.WEST) throw new IllegalArgumentException();
//        JPanel row = new JPanel();
//        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
//
//        for(int i = 0; i < field.getWidth(); ++i) {
//
//            BetweenCellsWidget southCellWidget = new BetweenCellsWidget(Orientation.HORIZONTAL);
//
//            row.add(southCellWidget);
//        }
//        return row;
//    }
}
