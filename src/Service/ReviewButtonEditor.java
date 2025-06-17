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

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import Repository.MongoDBConnection;
import UI.Auditor.AuditorDashboard;

/**
 *
 * @author Shari
 */
public class ReviewButtonEditor extends DefaultCellEditor {

    private JButton button;
    private boolean clicked;
    private JTable table;
    private AuditorDashboard auditor;

    public ReviewButtonEditor(JCheckBox checkBox, JTable table, AuditorDashboard auditor) {
        super(checkBox);
        this.table = table;
        this.auditor = auditor;
        button = new JButton("Review");
        button.setOpaque(true);

        button.addActionListener(e -> fireEditingStopped());

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            try {
                int selectedRow = table.getSelectedRow();
                String productId = String.valueOf(table.getValueAt(selectedRow, 0));
                Integer ecoScore = Integer.parseInt(String.valueOf(table.getValueAt(selectedRow, 5)));

                // Update MongoDB
                MongoDatabase db = MongoDBConnection.getDatabase();
                MongoCollection<Document> productCollection = db.getCollection("products");

                Document updateFields = new Document("ecoscore", ecoScore)
                        .append("is_audit", true);

                productCollection.updateOne(
                        new Document("product_id", productId),
                        new Document("$set", updateFields)
                );

                JOptionPane.showMessageDialog(button, "Product reviewed. Eco Score: " + ecoScore);
                SwingUtilities.invokeLater(() -> auditor.populateTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(button, "Error during audit: " + e.getMessage());
            }
        }
        clicked = false;
        return "Review";
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
