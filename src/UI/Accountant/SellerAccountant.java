/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Accountant;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import Repository.MongoDBConnection;
import UI.MainJFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.bson.types.ObjectId;

/**
 *
 * @author Shari
 */
public class SellerAccountant extends javax.swing.JPanel {

    /**
     * Creates new form SellerAccountant
     */
    MainJFrame mainpage;
    private String accountantUserId;

    public SellerAccountant(MainJFrame mainpage) {
        initComponents();
        this.accountantUserId = mainpage.userId;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(252, 243, 229)); // light beige

        // === Title Panel ===
        JLabel title = new JLabel("Seller Accountant", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(121, 85, 72)); // dark brown

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(121, 85, 72));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnLogout.addActionListener(e -> {
            mainpage.role = null;
            mainpage.dispose();
            new MainJFrame().setVisible(true);
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(new EmptyBorder(10, 20, 0, 20));
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(btnLogout, BorderLayout.WEST);

        // === Subtitle Panel ===
        JLabel subtitle = new JLabel("Shipping Payment Management", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.add(subtitle, BorderLayout.CENTER);
        subtitlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // === Table Styling ===
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(new Object[][]{}, new String[]{
            "_id", "Order Id", "Distance Travelled", "Invoice Amount", "Parcel Size", "Parcel Weight", "Payment Mode", "Status"
        }));
        styleTable(jTable1);

        JScrollPane scrollPane = new JScrollPane(jTable1);
        scrollPane.setBorder(new EmptyBorder(0, 20, 10, 20));

        // === Pay Button ===
        btnPay = new JButton("Pay");
        btnPay.setBackground(new Color(121, 85, 72));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFocusPainted(false);
        btnPay.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnPay.addActionListener(this::btnPayActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        buttonPanel.add(btnPay);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(getBackground());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(subtitlePanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadUnpaidShippingInvoices();
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(255, 236, 179)); // light yellow
        header.setForeground(new Color(121, 85, 72));   // brown
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnPay = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        LogoutBTN = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Shipping Cost");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Order Id", "Distance Travelled", "invoice Amount", "parcel Size", "parcel Weight", "Payment Mode", "status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnPay.setText("Pay");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Seller Accountant");

        LogoutBTN.setText("Logout");
        LogoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(347, 347, 347))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        // TODO add your handling code here:
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> financials = db.getCollection("ShippingFinancials");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to pay.");
            return;
        }

        // Get the internal MongoDB _id from a hidden column or stored value
        ObjectId documentId = (ObjectId) model.getValueAt(selectedRow, 0);  // Assuming _id is at column 0 and stored as ObjectId

        String paymentMode = model.getValueAt(selectedRow, 5).toString();
        String status = model.getValueAt(selectedRow, 6).toString();

        if (!status.equals("Paid")) {
            JOptionPane.showMessageDialog(this, "Please mark the status as 'Paid' before submitting payment.");
            return;
        }

        Document update = new Document("$set", new Document("payment_mode", paymentMode).append("status", status));

        financials.updateOne(new Document("_id", documentId), update);

        JOptionPane.showMessageDialog(this, " Payment status updated!");

        loadUnpaidShippingInvoices(); // Refresh table
    }//GEN-LAST:event_btnPayActionPerformed

    private void LogoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBTNActionPerformed
        // TODO add your handling code here:
        mainpage.role = null;
        mainpage.dispose();
        new MainJFrame().setVisible(true);
    }//GEN-LAST:event_LogoutBTNActionPerformed

    private void loadUnpaidShippingInvoices() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> financials = db.getCollection("ShippingFinancials");
        MongoCollection<Document> users = db.getCollection("users");

        // Get seller ID for this accountant
        Document userDoc = users.find(new Document("user_id", accountantUserId).append("is_active", true)).first();
        if (userDoc == null || !userDoc.containsKey("parent_seller_id")) {
            JOptionPane.showMessageDialog(this, "Seller ID not found for this accountant.");
            return;
        }

        String sellerId = userDoc.getString("parent_seller_id");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // clear table first

        for (Document doc : financials.find(new Document("status", "Pending").append("seller_id", sellerId))) {
            model.addRow(new Object[]{
                doc.getObjectId("_id"),
                doc.getString("order_id"),
                doc.getString("distance"),
                doc.get("invoice_amount"),
                doc.getString("parcel_size"),
                doc.getString("parcel_weight"),
                doc.getString("payment_mode") != null ? doc.getString("payment_mode") : "Cash",
                doc.getString("status")
            });
        }

        // Set dropdowns
        JComboBox<String> paymentCombo = new JComboBox<>(new String[]{"Cash", "Card", "Cheque"});
        jTable1.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(paymentCombo));

        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Paid"});
        jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusCombo));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogoutBTN;
    private javax.swing.JButton btnPay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
