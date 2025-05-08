/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Shari
 */

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        String columnName = table.getColumnName(column);
        if ("Actions".equals(columnName)) {
            setText((value == null) ? "Update" : value.toString());
        } else if ("Buy".equalsIgnoreCase(columnName)) {
            setText("Buy");
        } else {
            setText((value == null) ? "" : value.toString());
        }

        return this;
    }
}

