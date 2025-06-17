/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Accountant;

import Repository.MongoDBConnection;
import Service.SellerComboBoxEditor;
import UI.MainJFrame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Shari
 */
public class ShippingAccountantDashboard extends javax.swing.JPanel {

    MainJFrame mainpage;
    double cashInHand = 50000.00;
    String pid = "";

    /**
     * Creates new form ShippingAccountantDashboard
     */
    public ShippingAccountantDashboard(MainJFrame mainpage) {
        initComponents();
        this.mainpage = mainpage;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(252, 243, 229)); // beige

        // Title
        JLabel lblTitle = new JLabel("Shipping Accountant Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(121, 85, 72));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(121, 85, 72));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            mainpage.role = null;
            mainpage.dispose();
            new MainJFrame().setVisible(true);
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(new EmptyBorder(10, 20, 0, 20));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(logoutBtn, BorderLayout.WEST);

        // Inputs
        jTextField3 = new JTextField(16);
        jTextField3.addActionListener(e -> setCurrentDateTime());

        jTextField1 = new JTextField(10);
        jTextField1.addActionListener(e -> updateAmountPaid());

        jTextField2 = new JTextField(10);
        jTextField2.addActionListener(e -> updateCashInHand());

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(getBackground());
        infoPanel.setBorder(new EmptyBorder(10, 20, 0, 20));
        infoPanel.add(new JLabel("Date:"));
        infoPanel.add(jTextField3);
        infoPanel.add(new JLabel("Accounts Receivable:"));
        infoPanel.add(jTextField1);
        infoPanel.add(new JLabel("Cash In Hand:"));
        infoPanel.add(jTextField2);

        // Table 1
        jTable1 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Order ID", "Seller ID", "From", "To", "Parcel Size", "Parcel Weight", "Distance", "Invoice Amount", "Status"}
        ));
        styleTable(jTable1);
        JScrollPane scrollPane1 = new JScrollPane(jTable1);
        scrollPane1.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Submit button
        submitBtn.setBackground(new Color(121, 85, 72));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        submitBtn.addActionListener(this::submitBtnActionPerformed);

        lblSubmit.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblSubmit.setForeground(new Color(121, 85, 72));

        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setBackground(getBackground());
        submitPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        submitPanel.add(lblSubmit, BorderLayout.WEST);
        submitPanel.add(submitBtn, BorderLayout.EAST);

        // Table 2
        jTable2 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Order ID", "Seller ID", "From", "To", "Parcel Size", "Parcel Weight", "Distance", "Invoice Amount", "Status"}
        ));
        styleTable(jTable2);
        JScrollPane scrollPane2 = new JScrollPane(jTable2);
        scrollPane2.setBorder(new EmptyBorder(0, 20, 10, 20));

        // Final layout
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(getBackground());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(infoPanel, BorderLayout.SOUTH);

        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.setBackground(getBackground());
        southContainer.add(submitPanel, BorderLayout.NORTH);
        southContainer.add(scrollPane2, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane1, BorderLayout.CENTER);
        add(southContainer, BorderLayout.SOUTH);

        populateTable();
        updateAmountPaid();
        updateCashInHand();
        setCurrentDateTime();
        refreshSubmittedInvoices();
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(255, 236, 179));
        header.setForeground(new Color(121, 85, 72));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(255, 224, 178));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(252, 243, 229);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(255, 224, 178));
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
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

        submitBtn = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        LogoutBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblSubmit = new javax.swing.JLabel();

        submitBtn.setText("Submit");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("              Shipping Financials");

        LogoutBTN.setText("Logout");
        LogoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBTNActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Seller Id", "From", "To", "Parcel Size ", "Parcel Weight", "Distance Travelled ", "Invoice Amount", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setText("Amount Paid");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Accounts Receivable");

        jTextField2.setText("Cash In hand");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Cash In Hand");

        jTextField3.setText("Date");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Seller Id", "From", "To", "Parcel Size ", "Parcel Weight", "Distance Travelled ", "Invoice Amount", "Status"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        lblSubmit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSubmit.setText("Invoice Submitted");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
        // TODO add your handling code here:
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> financials = db.getCollection("ShippingFinancials");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        String orderId = model.getValueAt(selectedRow, 0).toString();
        String sellerId = model.getValueAt(selectedRow, 1).toString();
        String from = model.getValueAt(selectedRow, 2).toString();
        String to = model.getValueAt(selectedRow, 3).toString();
        String parcelSize = model.getValueAt(selectedRow, 4).toString();
        String parcelWeight = model.getValueAt(selectedRow, 5).toString();
        String distance = model.getValueAt(selectedRow, 6).toString();
        String invoiceAmount = model.getValueAt(selectedRow, 7).toString();
        String status = model.getValueAt(selectedRow, 8).toString();

        if (invoiceAmount.isEmpty() || parcelSize.isEmpty() || parcelWeight.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please fill all fields for the selected row.");
            return;
        }

        Document doc = new Document("order_id", orderId)
                .append("seller_id", sellerId)
                .append("from", from)
                .append("to", to)
                .append("parcel_size", parcelSize)
                .append("parcel_weight", parcelWeight)
                .append("distance", distance)
                .append("invoice_amount", Double.parseDouble(invoiceAmount))
                .append("status", status);

// Check if the record with the same order_id and seller_id exists
        Document existing = financials.find(new Document("order_id", orderId).append("seller_id", sellerId)).first();

        if (existing != null) {
            // Update if exists
            financials.updateOne(
                    new Document("order_id", orderId).append("seller_id", sellerId),
                    new Document("$set", doc)
            );
            javax.swing.JOptionPane.showMessageDialog(this, "✅ Record updated for existing seller.");
        } else {
            // Insert as new if seller_id is different
            financials.insertOne(doc);
            javax.swing.JOptionPane.showMessageDialog(this, "🆕 New seller entry inserted.");
        }
        refreshSubmittedInvoices();
    }//GEN-LAST:event_submitBtnActionPerformed

    private void LogoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBTNActionPerformed
        // TODO add your handling code here:
        mainpage.role = null;
        mainpage.dispose();
        new MainJFrame().setVisible(true);
    }//GEN-LAST:event_LogoutBTNActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        updateAmountPaid();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        updateCashInHand();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        setCurrentDateTime();
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void populateTable() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> orders = db.getCollection("orders");
        MongoCollection<Document> products = db.getCollection("products");
        MongoCollection<Document> shippingHistory = db.getCollection("shippingHistory");
        MongoCollection<Document> shippingFinancials = db.getCollection("ShippingFinancials");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Map<Integer, List<String>> rowSellerMap = new HashMap<>();
        int rowIndex = 0;
        for (Document doc : shippingHistory.find()) {
            String orderId = doc.getString("order_id");
            Document orderDoc = orders.find(new Document("_id", new ObjectId(orderId))).first();
            Document financeDoc = shippingFinancials.find(new Document("order_id", orderId)).first();

            // 1. Get product_ids from order
            List<String> productIds = (List<String>) orderDoc.get("product_ids"); // assuming this field

            Set<String> sellerIds = new HashSet<>();
            if (productIds != null) {
                for (String productId : productIds) {
                    pid = productId.replaceAll("[\\[\\]\"]", "");
                    System.out.print(pid);
                    Document productDoc = products.find(new Document("product_id", pid)).first();
                    if (productDoc != null) {
                        String sellerId = productDoc.getString("seller_id");
                        if (sellerId != null) {
                            sellerIds.add(sellerId);
                        }
                    }
                }
            }

            String toAddress = (orderDoc != null) ? orderDoc.getString("delivery_address") : "Unknown";

            String parcelSize = (financeDoc != null) ? financeDoc.getString("parcel_size") : "";
            String parcelWeight = (financeDoc != null) ? financeDoc.getString("parcel_weight") : "";
            String distance = (financeDoc != null) ? financeDoc.getString("distance") : "";
            String invoiceAmount = (financeDoc != null) ? String.valueOf(financeDoc.get("invoice_amount")) : "";
            String status = (financeDoc != null) ? financeDoc.getString("status") : "Pending";

            List<String> sellerList = new ArrayList<>(sellerIds);
            rowSellerMap.put(rowIndex, sellerList);

            model.addRow(new Object[]{
                orderId,
                sellerList.isEmpty() ? "Unknown" : sellerList.get(0),
                "Boston",
                toAddress,
                parcelSize,
                parcelWeight,
                distance,
                invoiceAmount,
                status
            });

            rowIndex++;

            // OPTIONAL: add JComboBox to the seller column if multiple sellerIds
            if (sellerIds.size() > 1) {
                JComboBox<String> comboBox = new JComboBox<>(sellerIds.toArray(new String[0]));
                jTable1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
            }
        }
        jTable1.getColumnModel().getColumn(1).setCellEditor(new SellerComboBoxEditor(rowSellerMap));
    }

    private void updateAmountPaid() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> shippingFinancials = db.getCollection("ShippingFinancials");

        double totalPaidAmount = 0.0;
        for (Document doc : shippingFinancials.find(new Document("status", "Paid"))) {
            Object amountObj = doc.get("invoice_amount");
            if (amountObj != null) {
                try {
                    totalPaidAmount += Double.parseDouble(amountObj.toString());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount in document: " + doc);
                }
            }
        }
        jTextField1.setText(String.valueOf(totalPaidAmount));
    }

    private void updateCashInHand() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> shippingFinancials = db.getCollection("ShippingFinancials");

        double totalPaidAmount = 0.0;
        for (Document doc : shippingFinancials.find(new Document("status", "Paid"))) {
            Object amountObj = doc.get("invoice_amount");
            if (amountObj != null) {
                try {
                    totalPaidAmount += Double.parseDouble(amountObj.toString());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount in document: " + doc);
                }
            }
        }

        double updatedCashInHand = cashInHand + totalPaidAmount;
        jTextField2.setText(String.valueOf(updatedCashInHand));
    }

    private void setCurrentDateTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        jTextField3.setText(now.format(formatter));
    }

    private void refreshSubmittedInvoices() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> financials = db.getCollection("ShippingFinancials");

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear table

        for (Document doc : financials.find()) {
            model.addRow(new Object[]{
                doc.getString("order_id"),
                doc.getString("seller_id"),
                doc.getString("from"),
                doc.getString("to"),
                doc.getString("parcel_size"),
                doc.getString("parcel_weight"),
                doc.getString("distance"),
                doc.get("invoice_amount"),
                doc.getString("status")
            });
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogoutBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblSubmit;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JButton submitBtn;
    // End of variables declaration//GEN-END:variables
}
