/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import Repository.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Shari
 */
public class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean clicked;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "Update" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            try {
                int selectedRow = table.getSelectedRow();

                String productId = String.valueOf(table.getValueAt(selectedRow, 0));
                String updatedName = String.valueOf(table.getValueAt(selectedRow, 1));
                String updatedDesc = String.valueOf(table.getValueAt(selectedRow, 3)); // Fixed
                String updatedMaterials = String.valueOf(table.getValueAt(selectedRow, 4)); // Fixed

                int carbonScore = 0;
                try {
                    carbonScore = Integer.parseInt(String.valueOf(table.getValueAt(selectedRow, 5))); // Fixed
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(button, "❗ CO2 Emission must be a valid integer.");
                    return label;
                }

                MongoDatabase db = MongoDBConnection.getDatabase();
                MongoCollection<Document> productCollection = db.getCollection("products");

                Document updateFields = new Document("product_name", updatedName)
                        .append("description", updatedDesc)
                        .append("materials_used", updatedMaterials)
                        .append("carbon_score", carbonScore);

                productCollection.updateOne(
                        new Document("product_id", productId),
                        new Document("$set", updateFields)
                );

                JOptionPane.showMessageDialog(button, "✅ Product updated successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(button, "Error updating product: " + ex.getMessage());
            }
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
