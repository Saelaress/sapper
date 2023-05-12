package sapper.ui;
import sapper.Cell;
import sapper.Field;
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
            JPanel row = createRow(i);
            add(row);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Cell cell = field.getCell(new Point(i, rowIndex));
            CellWidget cellWidget = new CellWidget(cell, isLightCell(i, rowIndex));
            cellsWidgets.put(cell, cellWidget);
            row.add(cellWidget);
        }
        return row;
    }

    private boolean isLightCell(int row, int col) {
        return (row % 2 == 0 & col % 2 == 0) || (row % 2 != 0 & col % 2 != 0);
    }
}
