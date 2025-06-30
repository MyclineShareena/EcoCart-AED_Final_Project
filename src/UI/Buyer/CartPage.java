/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Buyer;

import Model.CartItem;
import Model.CartItem;
import Model.CartItem;
import Model.CartManager;
import Model.CartManager;
import Model.CartManager;
import UI.Buyer.BuyerSplitPage;
import UI.Buyer.Products;
import UI.MainJFrame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Shari
 */
public class CartPage extends javax.swing.JPanel {

    MainJFrame mainpage;
    private JSplitPane splitPane;
    boolean isCheckoutClicked = false;
    String userId;

    /**
     * Creates new form CartPage
     */
    public CartPage(MainJFrame mainpage, String userId) {
        initComponents();
        this.userId = userId;
        this.mainpage = mainpage;
        populateCartTable();
        setupStyledUI();

    }

    private void setupStyledUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(232, 245, 233)); // soft green

        // === Title Panel ===
        lblTitle = new JLabel("🛒 Your Cart", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(46, 125, 50));

        BackBTN = new JButton("Back");
        BackBTN.setBackground(new Color(76, 175, 80));
        BackBTN.setForeground(Color.WHITE);
        BackBTN.setFocusPainted(false);
        BackBTN.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        BackBTN.addActionListener(evt -> {
            mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
            mainpage.revalidate();
            mainpage.repaint();
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(232, 245, 233));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(BackBTN, BorderLayout.WEST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // === Table Style ===
        jTable1.setRowHeight(25);
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jTable1.setGridColor(new Color(200, 200, 200));
        jTable1.setSelectionBackground(new Color(165, 214, 167));
        jTable1.setFillsViewportHeight(true);

        JTableHeader header = jTable1.getTableHeader();
        header.setBackground(new Color(200, 230, 201));
        header.setForeground(new Color(46, 125, 50));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(232, 245, 233);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(165, 214, 167));
                return c;
            }
        };
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // === Button Panel ===
        BtnDelete.setText("🗑 Remove Item");
        BtnDelete.setBackground(new Color(244, 67, 54));
        BtnDelete.setForeground(Color.WHITE);
        BtnDelete.setFocusPainted(false);
        BtnDelete.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        btnCheckOut.setText("✅ Check Out");
        btnCheckOut.setBackground(new Color(56, 142, 60));
        btnCheckOut.setForeground(Color.WHITE);
        btnCheckOut.setFocusPainted(false);
        btnCheckOut.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(232, 245, 233));
        buttonPanel.add(BtnDelete);
        buttonPanel.add(btnCheckOut);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // === Add Panels to Main Layout ===
        add(titlePanel, BorderLayout.NORTH);
        add(jScrollPane1, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BackBTN = new javax.swing.JButton();
        btnCheckOut = new javax.swing.JButton();
        BtnDelete = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(2000, 1100));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("View Cart ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Product Name", "Price", "Eco Score", "Quantity", "Total"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        BackBTN.setText("Back");
        BackBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTNActionPerformed(evt);
            }
        });

        btnCheckOut.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btnCheckOut.setText("Check out");
        btnCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOutActionPerformed(evt);
            }
        });

        BtnDelete.setText("Item Delete");
        BtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 1376, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnDelete))
                .addContainerGap(718, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTNActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
        mainpage.revalidate();
        mainpage.repaint();
    }//GEN-LAST:event_BackBTNActionPerformed

    private void btnCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOutActionPerformed
        // TODO add your handling code here:
        isCheckoutClicked = true;

        if (isCheckoutClicked) {
            List<CartItem> cartItems = CartManager.getCartItems();

            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, " Your cart is empty.");
                return;
            }

            // Calculate total
            List<String> productIdList = new ArrayList<>();
            int totalEcoScore = 0;
            int itemCount = 0;
            double grandTotal = 0;
            for (CartItem item : cartItems) {
                System.out.println("Printing Each : " + item.getEcoScore());
                grandTotal += item.getPrice() * item.getQuantity();
                productIdList.add(String.valueOf(item.getProductId()));
                totalEcoScore += item.getEcoScore();
                itemCount++;
            }
            System.out.println(itemCount);
            System.out.println(totalEcoScore);
            String ecoStatus = determineEcoStatus(totalEcoScore, itemCount);

            // Ask for delivery address
            String address = JOptionPane.showInputDialog(this, "Enter delivery address:");
            if (address == null || address.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Delivery address cannot be empty.");
                return;
            }

            // Confirm order
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Total: $" + grandTotal + "\nDeliver to: " + address + "\n\nPlace Order?",
                    "Confirm Order", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                MongoDatabase db = Repository.MongoDBConnection.getDatabase();
                MongoCollection<org.bson.Document> ordersCollection = db.getCollection("orders");

                org.bson.Document orderDoc = new org.bson.Document();
                orderDoc.append("delivery_address", address);
                orderDoc.append("userId", userId);
                orderDoc.append("total", grandTotal);
                orderDoc.append("status", "Pending");
                orderDoc.append("isDeleted", false);
                orderDoc.append("ordered_date", LocalDate.now().toString());
                orderDoc.append("ordered_time", LocalTime.now().toString());
                orderDoc.append("product_ids", productIdList);
                orderDoc.append("eco_status", ecoStatus);
                orderDoc.append("total_eco_score", totalEcoScore);

                ordersCollection.insertOne(orderDoc);
                JOptionPane.showMessageDialog(this, "✅ Order placed successfully!");
                CartManager.clearCart(); // clear cart after order
                mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
                mainpage.revalidate();
                mainpage.repaint();
            }
        }


    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void BtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select an item to delete.");
            return;
        }

        String selectedProductName = jTable1.getValueAt(selectedRow, 0).toString(); // Assuming column 0 is product name

        CartItem toRemove = null;
        for (CartItem item : CartManager.getCartItems()) {
            if (item.getProductName().equals(selectedProductName)) {
                toRemove = item;
                break;
            }
        }

        if (toRemove != null) {
            CartManager.getCartItems().remove(toRemove);
            JOptionPane.showMessageDialog(this, "Removed from cart: " + toRemove.getProductName());
            populateCartTable(); // Refresh cart view
        } else {
            JOptionPane.showMessageDialog(this, "Item not found in cart.");
        }

    }//GEN-LAST:event_BtnDeleteActionPerformed

    private void setupUI() {
        // Add a combo box dynamically
        JComboBox<Integer> quantityBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            quantityBox.addItem(i);
        }
        quantityBox.setBounds(300, 100, 100, 30);  // Set position manually or use layout
        this.add(quantityBox);

        // Add Proceed button
        JButton proceedBtn = new JButton("Proceed to Checkout");
        proceedBtn.setBounds(300, 150, 200, 30);  // Adjust as needed
        proceedBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Proceeding to checkout...");
            // You can redirect or process order here
        });
        this.add(proceedBtn);

        // Optional: repaint to reflect changes
        this.revalidate();
        this.repaint();
    }

    private void populateCartTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        List<CartItem> cartItems = CartManager.getCartItems();
        for (CartItem item : cartItems) {
            model.addRow(new Object[]{
                item.getProductName(),
                item.getPrice(),
                item.getEcoScore(),
                item.getQuantity(),
                item.getPrice() * item.getQuantity()
            });
        }
    }

    private String determineEcoStatus(int totalEcoScore, int itemCount) {
        if (itemCount == 0) {
            return "Unknown";
        }
        double average = (double) totalEcoScore / itemCount;

        if (average >= 7.0) {
            return "Good";
        } else if (average < 7 && average > 3.0) {
            return "Moderate";
        } else {
            return "Bad";
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN;
    private javax.swing.JButton BtnDelete;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
