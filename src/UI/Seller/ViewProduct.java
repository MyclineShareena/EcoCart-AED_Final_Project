/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Seller;

import Repository.MongoDBConnection;
import Service.AuditButtonEditor;
import Service.ButtonEditor;
import Service.ButtonRenderer;
import UI.MainJFrame;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

/**
 *
 * @author Shari
 */
public class ViewProduct extends javax.swing.JPanel {

    MainJFrame mainpage;
    String roleId;
    String userId;

    /**
     * Creates new form ViewProduct
     */
    public ViewProduct(MainJFrame mainpage, String roleId, String userId) {
        initComponents();
        this.mainpage = mainpage;
        this.roleId = roleId;
        this.userId = userId;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(232, 245, 253)); // Light blue background

        //Title Panel and back btn
        lblTitle = new JLabel("Seller Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(25, 118, 210));

        BackBTN = new JButton("Back");
        BackBTN.setBackground(new Color(25, 118, 210));
        BackBTN.setForeground(Color.WHITE);
        BackBTN.setFocusPainted(false);
        BackBTN.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        BackBTN.addActionListener(e -> {
            mainpage.setContentPane(new SellerSplitPage(mainpage));
            mainpage.revalidate();
            mainpage.repaint();
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(new EmptyBorder(10, 20, 0, 20));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(BackBTN, BorderLayout.WEST);

        //Subtitle
        lblTitleUpload = new JLabel("View & Edit Product", SwingConstants.CENTER);
        lblTitleUpload.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.add(lblTitleUpload, BorderLayout.CENTER);
        subtitlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        //Table
        viewEditTbl = new JTable();
        viewEditTbl.setModel(new DefaultTableModel(new Object[][]{}, new String[]{
            "Product ID", "Name", "Category", "Description", "Materials Used", "CO2 Emission",
            "Eco Score", "Action", "Submit Audit"
        }));

        styleTable(viewEditTbl);

        JScrollPane scrollPane = new JScrollPane(viewEditTbl);
        scrollPane.setBorder(new EmptyBorder(0, 20, 10, 20));

        //Buttons Panel
        BitAmount = new JButton("Bid Product");
        BitAmount.setBackground(new Color(25, 118, 210));
        BitAmount.setForeground(Color.WHITE);
        BitAmount.setFocusPainted(false);
        BitAmount.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        BitAmount.addActionListener(this::BitAmountActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(getBackground());
        buttonPanel.add(BitAmount);
        buttonPanel.setBorder(new EmptyBorder(5, 20, 15, 0));

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
        header.setBackground(new Color(187, 222, 251));
        header.setForeground(new Color(25, 118, 210));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(200, 200, 200));
        table.setSelectionBackground(new Color(144, 202, 249));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(232, 245, 253);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(144, 202, 249));
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public void populateTable() {
        DefaultTableModel model = (DefaultTableModel) viewEditTbl.getModel();
        model.setRowCount(0); // Clear existing rows

        MongoDatabase db = MongoDBConnection.getDatabase();
        if (db != null) {
            MongoCollection<Document> productCollection = db.getCollection("products");

            // Query only products uploaded by the current seller
            Document query = new Document("seller_id", userId);
            FindIterable<Document> products = productCollection.find(query);

            for (Document doc : products) {
                boolean isAudit = doc.getBoolean("is_audit", false);
                //int ecoScore = doc.get("ecoscore", 0);
                String auditButtonLabel = !isAudit ? "Submit for Audit" : "Submitted";
                Object[] row = new Object[]{
                    doc.getString("product_id"),
                    doc.getString("product_name"),
                    doc.getString("category"),
                    doc.getString("description"),
                    doc.getString("materials_used"),
                    doc.getInteger("carbon_score"),
                    doc.get("ecoscore", 0),
                    //doc.getString("certifier_id"),
                    //doc.getString("shipping_provider_id"),
                    "Update",
                    auditButtonLabel

                };
                model.addRow(row);
            }
            viewEditTbl.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
            viewEditTbl.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), viewEditTbl));

            viewEditTbl.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
            viewEditTbl.getColumnModel().getColumn(8).setCellEditor(new AuditButtonEditor(new JCheckBox(), viewEditTbl, this));

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
        lblTitleUpload = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewEditTbl = new javax.swing.JTable();
        BitAmount = new javax.swing.JButton();
        BackBTN = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Seller Dashboard");

        lblTitleUpload.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleUpload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleUpload.setText("View & Edit Product ");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 402));

        viewEditTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        viewEditTbl.setToolTipText("");
        viewEditTbl.setPreferredSize(new java.awt.Dimension(800, 400));
        jScrollPane1.setViewportView(viewEditTbl);
        if (viewEditTbl.getColumnModel().getColumnCount() > 0) {
            viewEditTbl.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            viewEditTbl.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            viewEditTbl.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            viewEditTbl.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        BitAmount.setText("Bid Product");
        BitAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BitAmountActionPerformed(evt);
            }
        });

        BackBTN.setText("Back");
        BackBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(BitAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(lblTitleUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(144, 144, 144))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblTitleUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BitAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BitAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BitAmountActionPerformed
        int selectedRow = viewEditTbl.getSelectedRow();

        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a product to place a bid.");
            return;
        }

        String productId = viewEditTbl.getValueAt(selectedRow, 0).toString();  // Assuming product ID is in column 0

        String input = javax.swing.JOptionPane.showInputDialog(this, "Enter your bid amount:");

        if (input == null || input.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Bid amount is required.");
            return;
        }

        double bidAmount = 0;
        try {
            bidAmount = Double.parseDouble(input);
            if (bidAmount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid positive number.");
            return;
        }

        com.mongodb.client.MongoDatabase db = Repository.MongoDBConnection.getDatabase();
        com.mongodb.client.MongoCollection<org.bson.Document> productsCollection = db.getCollection("products");

// Filter to find the correct product
        org.bson.Document filter = new org.bson.Document("product_id", productId);

// Update to set promotion status and bid amount
        org.bson.Document update = new org.bson.Document("$set", new org.bson.Document()
                .append("is_promoted", true)
                .append("is_bid_approved", false)
                .append("bid_status", "Pending")
                .append("bid_amount", bidAmount)
        );

// Perform the update
        productsCollection.updateOne(filter, update);

        javax.swing.JOptionPane.showMessageDialog(this, " Bid placed successfully!");
    }//GEN-LAST:event_BitAmountActionPerformed

    private void BackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTNActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new SellerSplitPage(mainpage));
        mainpage.invalidate();
        mainpage.validate();
    }//GEN-LAST:event_BackBTNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN;
    private javax.swing.JButton BitAmount;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleUpload;
    private javax.swing.JTable viewEditTbl;
    // End of variables declaration//GEN-END:variables
}
