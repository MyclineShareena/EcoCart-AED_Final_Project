/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.ProdAuditor;

import UI.MainJFrame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
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
public class ProductAuditorDashboard extends javax.swing.JPanel {

    MainJFrame mainpage;

    /**
     * Creates new form CertifierDashboard
     */
    public ProductAuditorDashboard(MainJFrame mainpage) {
        initComponents();
        this.mainpage = mainpage;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(232, 245, 255)); // light blue background

        // === Title Panel ===
        JLabel title = new JLabel("Product Auditor", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 70, 140)); // blue

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(0, 70, 140));
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
        JLabel subtitle = new JLabel("Review & Approve Bid Amount", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.add(subtitle, BorderLayout.CENTER);
        subtitlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // === Table ===
        viewEditTbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{
            "Seller ID", "Product Name", "Description", "Category", "Bid Amount($)"
        }));
        styleTable(viewEditTbl);

        JScrollPane scrollPane = new JScrollPane(viewEditTbl);
        scrollPane.setBorder(new EmptyBorder(0, 20, 20, 20));

        // === Buttons ===
        JButton btnApprove = new JButton("Approve");
        btnApprove.setBackground(new Color(0, 70, 140));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFocusPainted(false);
        btnApprove.addActionListener(this::btnApproveActionPerformed);

        JButton btnReject = new JButton("Reject");
        btnReject.setBackground(new Color(0, 70, 140));
        btnReject.setForeground(Color.WHITE);
        btnReject.setFocusPainted(false);
        btnReject.addActionListener(this::btnRejectActionPerformed);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(getBackground());
        btnPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        btnPanel.add(btnApprove);
        btnPanel.add(Box.createHorizontalStrut(20));
        btnPanel.add(btnReject);

        // Add components to main layout
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(getBackground());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(subtitlePanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        populateBidTable();
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(179, 229, 252));
        header.setForeground(new Color(0, 70, 140));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(180, 220, 240));
        table.setSelectionBackground(new Color(200, 230, 255));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(232, 245, 255);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(200, 230, 255));
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

        jScrollPane1 = new javax.swing.JScrollPane();
        viewEditTbl = new javax.swing.JTable();
        lblTitleReview = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        btnApprove = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        LogoutBTN = new javax.swing.JButton();

        viewEditTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Seller ID", "Product Name", "Description", "Category", "Bid Amount"
            }
        ));
        viewEditTbl.setToolTipText("");
        jScrollPane1.setViewportView(viewEditTbl);

        lblTitleReview.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleReview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleReview.setText("Review & Approve Bit Amount");

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Product Auditor");

        btnApprove.setText("Approve");
        btnApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApproveActionPerformed(evt);
            }
        });

        btnReject.setText("Reject");
        btnReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRejectActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(lblTitleReview, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitleReview, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApprove)
                    .addComponent(btnReject))
                .addGap(173, 173, 173))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void populateBidTable() {
        MongoDatabase db = Repository.MongoDBConnection.getDatabase();
        MongoCollection<Document> productCollection = db.getCollection("products");

        DefaultTableModel model = (DefaultTableModel) viewEditTbl.getModel();
        model.setRowCount(0);  // Clear existing rows

        for (Document doc : productCollection.find(
                new Document("is_promoted", true).append("bid_status", "Pending"))) {
            model.addRow(new Object[]{
                doc.getString("seller_id"),
                doc.getString("product_name"),
                doc.getString("description"),
                doc.getString("category"),
                doc.get("bid_amount")
            });
        }
    }

    private void btnRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRejectActionPerformed
        int selectedRow = viewEditTbl.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a row to reject.");
            return;
        }

        String productName = viewEditTbl.getValueAt(selectedRow, 1).toString();
        MongoDatabase db = Repository.MongoDBConnection.getDatabase();
        MongoCollection<Document> productCollection = db.getCollection("products");

        Document filter = new Document("product_name", productName);
        Document update = new Document("$set", new Document("bid_status", "Rejected").append("is_promoted", false));

        productCollection.updateOne(filter, update);
        javax.swing.JOptionPane.showMessageDialog(this, "Bid rejected for product: " + productName);
        populateBidTable();
    }//GEN-LAST:event_btnRejectActionPerformed

    private void btnApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApproveActionPerformed
        int selectedRow = viewEditTbl.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a row to approve.");
            return;
        }

        String productName = viewEditTbl.getValueAt(selectedRow, 1).toString();
        MongoDatabase db = Repository.MongoDBConnection.getDatabase();
        MongoCollection<Document> productCollection = db.getCollection("products");

        Document filter = new Document("product_name", productName); // is_bid_approved
        Document update = new Document("$set", new Document("bid_status", "Approved").append("is_promoted", true).append("is_bid_approved", true));

        productCollection.updateOne(filter, update);
        javax.swing.JOptionPane.showMessageDialog(this, "Bid approved for product: " + productName);
        populateBidTable();
    }//GEN-LAST:event_btnApproveActionPerformed

    private void LogoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBTNActionPerformed
        // TODO add your handling code here:
        mainpage.role = null;
        mainpage.dispose();
        new MainJFrame().setVisible(true);
    }//GEN-LAST:event_LogoutBTNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogoutBTN;
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnReject;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleReview;
    private javax.swing.JTable viewEditTbl;
    // End of variables declaration//GEN-END:variables
}
