/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.*;
import java.util.List;
/**
 *
 * @author Shari
 */

public class SellerComboBoxEditor extends AbstractCellEditor implements TableCellEditor {
    private JComboBox<String> comboBox;
    private Map<Integer, List<String>> rowSellerMap;

    public SellerComboBoxEditor(Map<Integer, List<String>> rowSellerMap) {
        this.rowSellerMap = rowSellerMap;
        comboBox = new JComboBox<>();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        comboBox.removeAllItems();
        List<String> sellers = rowSellerMap.getOrDefault(row, Collections.singletonList("Unknown"));
        for (String seller : sellers) {
            comboBox.addItem(seller);
        }
        comboBox.setSelectedItem(value != null ? value.toString() : sellers.get(0));
        return comboBox;
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }
}
