package Service;

import Model.CartItem;
import Model.CartManager;
import UI.Buyer.CartPage;
import UI.MainJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CartButtonEditor extends DefaultCellEditor {

    private final JButton button;
    private final JTable table;
    private boolean clicked;
    private final String userId;

    public CartButtonEditor(JCheckBox checkBox, JTable table, String userId) {
        super(checkBox);
        this.table = table;
        this.userId = userId;
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
            double price = Double.parseDouble(table.getValueAt(row, 3).toString());
            int ecoScore = Integer.parseInt(table.getValueAt(row, 4).toString());

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
                int qty = showQuantityDialog(button);
                if (qty == -1) return "Add to Cart"; // Cancelled

                List<String> productIds = new ArrayList<>();
                productIds.add(productId);

                CartManager.addToCart(new CartItem(productIds, productName, price, ecoScore, qty));
                JOptionPane.showMessageDialog(button, "✅ " + productName + " added to cart!");
            }

            // ✅ Refresh UI with Cart Page
            MainJFrame mainFrame = (MainJFrame) SwingUtilities.getWindowAncestor(table);
            mainFrame.setContentPane(new CartPage(mainFrame, userId));
            mainFrame.revalidate();
            mainFrame.repaint();
        }

        clicked = false;
        return "Add to Cart";
    }

    private int showQuantityDialog(Component parent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel label = new JLabel("Enter quantity:");
        JTextField quantityField = new JTextField("1", 5);
        panel.add(label);
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Add to Cart",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int qty = Integer.parseInt(quantityField.getText().trim());
                return (qty > 0) ? qty : 1;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Invalid input. Defaulting to quantity 1.");
                return 1;
            }
        }

        return -1; // Cancelled
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}
