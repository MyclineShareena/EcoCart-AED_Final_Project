/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.ShippingCoordinator;

import Repository.MongoDBConnection;
import UI.MainJFrame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Shari
 */
public class ShippingCoordinatorDashboard extends javax.swing.JPanel {

    MainJFrame mainpage;

    /**
     * Creates new form ShippingProviderDashboard
     */
    public ShippingCoordinatorDashboard(MainJFrame mainpage) {
        initComponents();
        this.mainpage = mainpage;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(252, 243, 229)); // light beige

        // Title
        JLabel title = new JLabel("Shipping Coordinator", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(121, 85, 72));

        LogoutBTN = new JButton("Logout");
        LogoutBTN.setBackground(new Color(121, 85, 72));
        LogoutBTN.setForeground(Color.WHITE);
        LogoutBTN.setFocusPainted(false);
        LogoutBTN.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        LogoutBTN.addActionListener(e -> {
            mainpage.role = null;
            mainpage.dispose();
            new MainJFrame().setVisible(true);
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(new EmptyBorder(10, 20, 0, 20));
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(LogoutBTN, BorderLayout.WEST);

        JLabel subtitle = new JLabel("Shipping History Management", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        subtitle.setForeground(new Color(93, 64, 55));

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.add(subtitle, BorderLayout.CENTER);
        subtitlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Table
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(new Object[][]{}, new String[]{
            "Order ID", "From", "To", "Shipping Method", "Status", "Co2 Emission"
        }));
        styleTable(jTable1);

        JScrollPane scrollPane = new JScrollPane(jTable1);
        scrollPane.setBorder(new EmptyBorder(0, 20, 10, 20));

        // Submit button
        submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(121, 85, 72));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        submitBtn.addActionListener(this::submitBtnActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        buttonPanel.add(submitBtn);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(getBackground());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(subtitlePanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        populateTable();
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

        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Shipped", "Delivered"});
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(statusCombo));

        JComboBox<String> shippingCombo = new JComboBox<>(new String[]{"Air", "Ground", "Sea"});
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(shippingCombo));
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
        LogoutBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        submitBtn = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("Shipping Coordinator Dashboard");

        LogoutBTN.setText("Logout");
        LogoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBTNActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "From", "To", "Shipping Method", "Status", "Co2 Emission"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        submitBtn.setText("Submit");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(607, 607, 607)
                        .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBTNActionPerformed
        // TODO add your handling code here:
        mainpage.role = null;
        mainpage.dispose();
        new MainJFrame().setVisible(true);
    }//GEN-LAST:event_LogoutBTNActionPerformed

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
        // TODO add your handling code here:
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> shippingHistoryCollection = db.getCollection("shippingHistory");
        MongoCollection<Document> ordersCollection = db.getCollection("orders");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int rowCount = model.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String orderId = model.getValueAt(i, 0).toString();
            String shippingMethod = model.getValueAt(i, 3).toString();
            String status = model.getValueAt(i, 4).toString();
            Object co2Value = model.getValueAt(i, 5);
            double co2Emission = 0.0;

            try {
                if (co2Value != null && !co2Value.toString().trim().isEmpty()) {
                    co2Emission = Double.parseDouble(co2Value.toString().trim());
                } else {
                    // Skip saving this row if co2 is blank
                    continue;
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Invalid CO2 emission at row " + (i + 1));
                return;
            }

            Document filter = new Document("_id", new ObjectId(orderId));  // Use correct identifier
            Document update = new Document("$set", new Document("status", status));

            ordersCollection.updateOne(filter, update, new UpdateOptions().upsert(true));

            Document historyDoc = new Document("order_id", orderId)
                    .append("shipping_method", shippingMethod)
                    .append("status", status)
                    .append("co2_emission", co2Emission);

            // Insert or update based on order_id
            shippingHistoryCollection.updateOne(
                    new Document("order_id", orderId),
                    new Document("$set", historyDoc),
                    new com.mongodb.client.model.UpdateOptions().upsert(true)
            );

        }

        javax.swing.JOptionPane.showMessageDialog(this, "✅ Shipping history updated successfully!");
        populateTable();
    }//GEN-LAST:event_submitBtnActionPerformed

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> ordersCollection = db.getCollection("orders");
        MongoCollection<Document> shippingHistoryCollection = db.getCollection("shippingHistory");

        for (Document doc : ordersCollection.find()) {
            Document historyDoc = shippingHistoryCollection.find(new Document("order_id", doc.getObjectId("_id").toHexString())).first();
            String shippingMethod = (historyDoc != null && historyDoc.getString("shipping_method") != null)
                    ? historyDoc.getString("shipping_method")
                    : "Select Mode";
            String co2 = (historyDoc != null && historyDoc.get("co2_emission") != null)
                    ? String.valueOf(historyDoc.get("co2_emission"))
                    : "";
            model.addRow(new Object[]{
                doc.getObjectId("_id").toHexString(),
                "Boston", // Static value
                doc.getString("delivery_address"),
                shippingMethod,
                doc.getString("status") != null ? doc.getString("status") : "Pending",
                co2
            });
        }

        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Shipped", "Delivered"});
        jTable1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(statusCombo));

        JComboBox<String> shippingCombo = new JComboBox<>(new String[]{"Air", "Ground", "Sea"});
        jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(shippingCombo));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogoutBTN;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JButton submitBtn;
    // End of variables declaration//GEN-END:variables
}
