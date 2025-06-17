/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Customersupport;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import Repository.MongoDBConnection;
import Service.EmailUtil;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import javax.swing.table.DefaultTableModel;

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

/**
 *
 * @author Shari
 */
public class SupportPage extends javax.swing.JPanel {

    MainJFrame mainpage;
    String userId;

    /**
     * Creates new form Customersupport
     */
    public SupportPage(MainJFrame mainpage, String userId) {
        initComponents();
        this.userId = userId;
        this.mainpage = mainpage;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 240, 245)); // light pink background

        // === Title Panel ===
        JLabel title = new JLabel("Customer Support", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(199, 21, 133)); // deep pink

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(199, 21, 133));
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

        // === Subtitle ===
        JLabel subtitle = new JLabel("View Issues", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        subtitlePanel.add(subtitle, BorderLayout.CENTER);

        // === Table ===
        viewEditTbl = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Order ID", "Product Name", "Description", "Order Status", "Order Date", "Issue Type"}
        ));
        styleTable(viewEditTbl);
        JScrollPane scrollPane = new JScrollPane(viewEditTbl);
        scrollPane.setBorder(new EmptyBorder(0, 20, 10, 20));

        // === Button Panel ===
        JButton respondBtn = new JButton("Send Response");
        respondBtn.setBackground(new Color(199, 21, 133));
        respondBtn.setForeground(Color.WHITE);
        respondBtn.setFocusPainted(false);
        respondBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        respondBtn.addActionListener(this::jButton1ActionPerformed);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        buttonPanel.add(respondBtn);

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
        header.setBackground(new Color(255, 182, 193)); // light pink header
        header.setForeground(new Color(139, 0, 139));   // dark magenta text
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(255, 192, 203));
        table.setSelectionBackground(new Color(255, 228, 225));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(255, 240, 245);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(255, 228, 225));
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

        lblTitle = new javax.swing.JLabel();
        lblTitleReview = new javax.swing.JLabel();
        LogoutBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewEditTbl = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Customer Support");

        lblTitleReview.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleReview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleReview.setText("View Issues");

        LogoutBTN.setText("Logout");
        LogoutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBTNActionPerformed(evt);
            }
        });

        viewEditTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "orderId", "Product Name", "Description", "Order Status", "Order Date", "Issue Type"
            }
        ));
        viewEditTbl.setToolTipText("");
        jScrollPane1.setViewportView(viewEditTbl);

        jButton1.setText("Send Response");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(278, 278, 278)
                        .addComponent(lblTitleReview, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogoutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitleReview, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBTNActionPerformed
        // TODO add your handling code here:
        mainpage.role = null;
        mainpage.dispose();
        new MainJFrame().setVisible(true);
    }//GEN-LAST:event_LogoutBTNActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = viewEditTbl.getSelectedRow();

        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a record from the table.");
            return;
        }

        // Get unique identifiers to locate the document
        String orderId = viewEditTbl.getValueAt(selectedRow, 0).toString();
        String productName = viewEditTbl.getValueAt(selectedRow, 1).toString();
        String description = viewEditTbl.getValueAt(selectedRow, 2).toString();

        // Create dialog
        javax.swing.JDialog responseDialog = new javax.swing.JDialog();
        responseDialog.setTitle("Send Response");
        responseDialog.setModal(true);
        responseDialog.setSize(700, 400);
        responseDialog.setLocationRelativeTo(this);

        // Components
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(10, 30);
        javax.swing.JButton sendButton = new javax.swing.JButton("Send");

        sendButton.addActionListener(e -> {
            String responseText = textArea.getText().trim();
            if (responseText.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(responseDialog, "Response cannot be empty.");
                return;
            }
            System.out.println(orderId);
            try {
                MongoDatabase db = MongoDBConnection.getDatabase();
                MongoCollection<Document> issueCollection = db.getCollection("customerIssue");
                MongoCollection<Document> userCollection = db.getCollection("users");

                // Identify the issue record using product name and description
                Document issueQuery = new Document("order_id", orderId);

                Document issueDoc = issueCollection.find(issueQuery).first();

                System.out.println(issueDoc.get("userId"));

                if (issueDoc == null || issueDoc.get("userId") == null) {
                    javax.swing.JOptionPane.showMessageDialog(responseDialog, "User ID not found in issue record.");
                    return;
                }

                String targetUserId = issueDoc.get("userId").toString();

                // Fetch user document by user_id
                Document userDoc = userCollection.find(new Document("user_id", targetUserId)).first();
                if (userDoc == null || userDoc.getString("email") == null) {
                    javax.swing.JOptionPane.showMessageDialog(responseDialog, "User email not found.");
                    return;
                }

                String userEmail = userDoc.getString("email");

                // Send email
                EmailUtil.sendEmail(
                        userEmail,
                        "Support Response to Your Issue: " + productName,
                        "Dear user,\n\n" + responseText + "\n\nRegards,\nSupport Team"
                );

                // Update issue document
                Document update = new Document("$set", new Document("support_response", responseText)
                        .append("response_sent", true));
                issueCollection.updateOne(issueQuery, update);

                javax.swing.JOptionPane.showMessageDialog(responseDialog, "Response saved and email sent!");
                responseDialog.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(responseDialog, "Error sending email or updating database.");
            }
        });

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.BorderLayout());
        panel.add(new javax.swing.JScrollPane(textArea), java.awt.BorderLayout.CENTER);
        panel.add(sendButton, java.awt.BorderLayout.SOUTH);

        responseDialog.add(panel);
        responseDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void populateTable() {
        try {
            MongoDatabase db = MongoDBConnection.getDatabase(); // Gets DB from your custom connection handler
            MongoCollection<Document> collection = db.getCollection("customerIssue");

            DefaultTableModel model = (DefaultTableModel) viewEditTbl.getModel();
            model.setRowCount(0); // Clear existing rows

            FindIterable<Document> docs = collection.find();

            for (Document doc : docs) {
                Object orderId = doc.get("order_id");
                Object productName = doc.get("product_name");
                Object description = doc.get("description");
                Object status = doc.get("order_status");
                Object orderDate = doc.get("order_date");
                Object issueType = doc.get("issue_type");

                model.addRow(new Object[]{
                    orderId != null ? orderId.toString() : "",
                    productName != null ? productName.toString() : "",
                    description != null ? description.toString() : "",
                    status != null ? status.toString() : "",
                    orderDate != null ? orderDate.toString() : "",
                    issueType != null ? issueType.toString() : ""
                });
            }

        } catch (MongoException me) {
            System.err.println("MongoDB error: " + me.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogoutBTN;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleReview;
    private javax.swing.JTable viewEditTbl;
    // End of variables declaration//GEN-END:variables
}
