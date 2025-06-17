/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Repository.MongoDBConnection;
import UI.Seller.ViewProduct;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import org.bson.Document;

/**
 *
 * @author Shari
 */
public class AuditButtonEditor extends DefaultCellEditor {
    private JButton button;
    private JTable table;
    private boolean clicked;
    private ViewProduct product;

    public AuditButtonEditor(JCheckBox checkBox, JTable table, ViewProduct product) {
        super(checkBox);
        this.table = table;
        this.product = product;
        this.button = new JButton();
        this.button.setOpaque(true);
        this.button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
        String label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.setEnabled(!"Submitted".equals(label));
        clicked = !"Submitted".equals(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            int row = table.getSelectedRow();
            String productId = table.getValueAt(row, 0).toString();

            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> productCollection = db.getCollection("products");

            productCollection.updateOne(
                new Document("product_id", productId),
                new Document("$set", new Document("is_audit", true))
            );
            //SwingUtilities.invokeLater(() -> product.populateTable());
            JOptionPane.showMessageDialog(button, "Submitted for audit!");
            table.setValueAt("Submitted", row, 8);
            SwingUtilities.invokeLater(() -> product.populateTable());
        }
        clicked = false;
        return button.getText();
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}

