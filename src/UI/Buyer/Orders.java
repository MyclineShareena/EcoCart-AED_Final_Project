/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Buyer;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import Repository.MongoDBConnection;
import UI.MainJFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Shari
 */
public class Orders extends javax.swing.JPanel {

    MainJFrame mainpage;
    String userId;

    /**
     * Creates new form Orders
     */
    public Orders(MainJFrame mainpage, String userId) {
        this.mainpage = mainpage;
        this.userId = userId;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(232, 245, 233));

        // === Title Panel ===
        lblTitle = new JLabel("Buyer Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(46, 125, 50));

        BackBTN = new JButton("Back");
        BackBTN.setBackground(new Color(76, 175, 80));
        BackBTN.setForeground(Color.WHITE);
        BackBTN.setFocusPainted(false);
        BackBTN.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        BackBTN.addActionListener(e -> {
            mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
            mainpage.revalidate();
            mainpage.repaint();
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(BackBTN, BorderLayout.WEST);

        // === Subtitle ===
        lblTitleProd = new JLabel("Orders History", SwingConstants.CENTER);
        lblTitleProd.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.add(lblTitleProd, BorderLayout.CENTER);
        subtitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // === Table ===
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"Order ID", "Product Name", "Order Date", "Order Status", "Eco Status"}) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        styleTable(jTable1);

        jScrollPane1 = new JScrollPane(jTable1);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // === Buttons Panel ===
        CancelOrder1 = new JButton("Cancel Order");
        CancelOrder1.setBackground(new Color(244, 81, 30)); // red-orange
        CancelOrder1.setForeground(Color.WHITE);
        CancelOrder1.setFocusPainted(false);
        CancelOrder1.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        CancelOrder1.addActionListener(this::CancelOrder1ActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(getBackground());
        buttonPanel.add(CancelOrder1);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 0));

        // === Add to Main Layout ===
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(232, 245, 233));
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(subtitlePanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(jScrollPane1, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        populateOrderTable();
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(200, 230, 201));
        header.setForeground(new Color(46, 125, 50));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(200, 200, 200));
        table.setSelectionBackground(new Color(165, 214, 167));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(232, 245, 233);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(165, 214, 167));
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void populateOrderTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> ordersCollection = db.getCollection("orders");
        MongoCollection<Document> productsCollection = db.getCollection("products");

        try (MongoCursor<Document> cursor = ordersCollection.find(new Document("isDeleted", false).append("userId", userId)).iterator()) {
            while (cursor.hasNext()) {
                Document order = cursor.next();

                String orderId = order.getObjectId("_id").toString(); // Or use your own order_id field if exists
                String orderDate = order.getString("ordered_date");
                String status = order.getString("status");
                String ecoStatus = order.getString("eco_status");
                List<String> productIds = order.getList("product_ids", String.class);
                List<String> productNames = new ArrayList<>();

                if (productIds != null) {
                    for (String pid : productIds) {
                        pid = pid.replaceAll("[\\[\\]\"]", "");
                        Document product = productsCollection.find(new Document("product_id", pid.toString())).first();
                        if (product != null) {
                            productNames.add(product.getString("product_name"));
                        }
                    }
                }

                String productNamesStr = String.join(", ", productNames);

                model.addRow(new Object[]{
                    orderId,
                    productNamesStr,
                    orderDate,
                    status,
                    ecoStatus
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        BackBTN = new javax.swing.JButton();
        lblTitleProd = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        CancelOrder1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(2000, 1100));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Buyer Dashboard");

        BackBTN.setText("Back");
        BackBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTNActionPerformed(evt);
            }
        });

        lblTitleProd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleProd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleProd.setText("Orders History");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Product Name", "Order Date", "Order Status", "Eco Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        CancelOrder1.setText("Cancel Order");
        CancelOrder1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelOrder1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(113, 113, 113)
                                        .addComponent(lblTitleProd, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(CancelOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(111, 111, 111))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(lblTitleProd)
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(CancelOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(596, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTNActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
        mainpage.invalidate();
        mainpage.validate();
    }//GEN-LAST:event_BackBTNActionPerformed

    private void CancelOrder1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelOrder1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select an order to cancel.");
            return;
        }

        String orderId = jTable1.getValueAt(selectedRow, 0).toString();
        String orderStatus = jTable1.getValueAt(selectedRow, 3).toString();

        if (!orderStatus.equalsIgnoreCase("Pending")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Only orders with 'Pending' status can be cancelled.");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this order?",
                "Confirm Cancellation",
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> ordersCollection = db.getCollection("orders");

            ordersCollection.updateOne(
                    new Document("_id", new org.bson.types.ObjectId(orderId)),
                    new Document("$set", new Document("isDeleted", true))
            );

            javax.swing.JOptionPane.showMessageDialog(this, "Order cancelled successfully.");
            populateOrderTable(); // refresh table
        }
    }//GEN-LAST:event_CancelOrder1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN;
    private javax.swing.JButton CancelOrder1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleProd;
    // End of variables declaration//GEN-END:variables
}
