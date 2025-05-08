/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.CartItem;
import Model.CartManager;
import Model.CartPage;
import UI.MainJFrame;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Shari
 */
public class CartButtonEditor extends DefaultCellEditor {

    private final JButton button;
    private final JTable table;
    private boolean clicked;

    public CartButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        this.button = new JButton("Add to Cart");
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
            int row = table.getSelectedRow();
            String productId = table.getValueAt(row, 0).toString();
            String productName = table.getValueAt(row, 1).toString();
            double price = Double.parseDouble(table.getValueAt(row, 2).toString());
            int ecoScore = Integer.parseInt(table.getValueAt(row, 3).toString());

            boolean itemExists = false;

            for (CartItem item : CartManager.getCartItems()) {
                if (item.getProductId().contains(productId)) {
                    item.setQuantity(item.getQuantity() + 1); // ✅ Only increase quantity
                    JOptionPane.showMessageDialog(button, "✅ Quantity updated for: " + item.getProductName());
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                String input = JOptionPane.showInputDialog(button, "Enter quantity:", "1");

                if (input == null) {
                    // User clicked cancel or closed the dialog — do nothing
                    return "Add to Cart";
                }

                int qty = 1;
                try {
                    qty = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(button, "Invalid quantity entered. Defaulting to 1.");
                }

                List<String> productIds = new ArrayList<>();
                productIds.add(productId);

                CartManager.addToCart(new CartItem(productIds, productName, price, ecoScore, qty));
                JOptionPane.showMessageDialog(button, "✅ " + productName + " added to cart!");

            }

            // Refresh UI
            MainJFrame mainFrame = (MainJFrame) SwingUtilities.getWindowAncestor(table);
            mainFrame.setContentPane(new CartPage(mainFrame));
            mainFrame.revalidate();
            mainFrame.repaint();
        }

        clicked = false;

        return "Add to Cart";
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}
