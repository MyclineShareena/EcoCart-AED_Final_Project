/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Buyer;

import UI.MainJFrame;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import Repository.MongoDBConnection;
import com.mongodb.MongoException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Shari
 */
public class RaiseIssue extends javax.swing.JPanel {

    MainJFrame mainpage;
    private List<Document> ordersList = new ArrayList<>();
    String userId;

    /**
     * Creates new form RaiseIssue
     */
    public RaiseIssue(MainJFrame mainpage, String userId) {
        initComponents();
        this.userId = userId;
        this.mainpage = mainpage;
        populateOrderData();
        setupComboBoxListeners();
        setupComplainTypes();
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

// Apply to inputs
        jComboBox1.setFont(inputFont);
        jComboBox4.setFont(inputFont);
        TypeOfComplain.setFont(inputFont);
        jTextField1.setFont(inputFont);
        jTextField2.setFont(inputFont);
        jTextArea1.setFont(inputFont);
        SubmitBtn.setFont(buttonFont);

// Apply to labels if you define them separately in GridBag layout
        jComboBox1.setPreferredSize(new Dimension(250, 35));
        jComboBox4.setPreferredSize(new Dimension(250, 35));
        TypeOfComplain.setPreferredSize(new Dimension(250, 35));
        jTextField1.setPreferredSize(new Dimension(250, 35));
        jTextField2.setPreferredSize(new Dimension(250, 35));
        jTextArea1.setPreferredSize(new Dimension(250, 100));

        setBackground(new Color(232, 245, 233));
        setLayout(new BorderLayout(10, 10));

// === Title and Back Button Panel ===
        JLabel lblTitle = new JLabel("Buyer Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(46, 125, 50));

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(new Color(76, 175, 80));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backBtn.addActionListener(e -> {
            mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
            mainpage.revalidate();
            mainpage.repaint();
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(backBtn, BorderLayout.WEST);

// === Subtitle ===
        JLabel lblSubtitle = new JLabel("Raise an Issue", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        subtitlePanel.add(lblSubtitle, BorderLayout.CENTER);

// === Form Panel ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Reset model for dropdown if needed
        TypeOfComplain.setModel(new DefaultComboBoxModel<>(new String[]{
            "Damaged Product",
            "Delivered Wrong Product",
            "Variation in Size",
            "Delivered Late than expected",
            "Other"
        }));

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblOrderId = new JLabel("Order ID:");
        lblOrderId.setFont(labelFont);
        formPanel.add(lblOrderId, gbc);
        gbc.gridx = 1;
        formPanel.add(jComboBox1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblOrderStatus = new JLabel("Order Status:");
        lblOrderStatus.setFont(labelFont);
        formPanel.add(lblOrderStatus, gbc);
        gbc.gridx = 1;
        formPanel.add(jTextField1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblOrderDate = new JLabel("Order Date:");
        lblOrderDate.setFont(labelFont);
        formPanel.add(lblOrderDate, gbc);
        gbc.gridx = 1;
        formPanel.add(jTextField2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblProduct = new JLabel("Product:");
        lblProduct.setFont(labelFont);
        formPanel.add(lblProduct, gbc);
        gbc.gridx = 1;
        formPanel.add(jComboBox4, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblIssueType = new JLabel("Issue Type:");
        lblIssueType.setFont(labelFont);
        formPanel.add(lblIssueType, gbc);
        gbc.gridx = 1;
        formPanel.add(TypeOfComplain, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(labelFont);
        formPanel.add(lblDescription, gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(jTextArea1), gbc);
        Color ecoBg = new Color(232, 245, 233);

// Set background and border
        jComboBox1.setBackground(ecoBg);
        jComboBox1.setOpaque(true);

        jComboBox4.setBackground(ecoBg);
        jComboBox4.setOpaque(true);

        TypeOfComplain.setBackground(ecoBg);
        TypeOfComplain.setOpaque(true);

// Optional: set renderer for full theme matching
        javax.swing.plaf.basic.BasicComboBoxRenderer renderer = new javax.swing.plaf.basic.BasicComboBoxRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                java.awt.Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (!isSelected) {
                    c.setBackground(ecoBg);
                }
                return c;
            }
        };

        jComboBox1.setRenderer(renderer);
        jComboBox4.setRenderer(renderer);
        TypeOfComplain.setRenderer(renderer);

// === Submit Button ===
        SubmitBtn.setBackground(new Color(76, 175, 80));
        SubmitBtn.setForeground(Color.WHITE);
        SubmitBtn.setFocusPainted(false);
        SubmitBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // instead of CENTER
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.add(SubmitBtn);

// === Assembl// === Assemble All Panels ===
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(getBackground());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(subtitlePanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 60, 10)); // Align left with padding
        wrapper.setBackground(getBackground());
        wrapper.add(formPanel);
        add(wrapper, BorderLayout.CENTER);

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

        lblTitle1 = new javax.swing.JLabel();
        lblTitleProd1 = new javax.swing.JLabel();
        BackBTN1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        TypeOfComplain = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        SubmitBtn = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(2000, 1100));

        lblTitle1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Buyer Dashboard");

        lblTitleProd1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleProd1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleProd1.setText("Raise a Issue");

        BackBTN1.setText("Back");
        BackBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTN1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        TypeOfComplain.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        TypeOfComplain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TypeOfComplainActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        SubmitBtn.setText("Submit");
        SubmitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(lblTitleProd1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(97, 97, 97)
                        .addComponent(BackBTN1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TypeOfComplain, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(574, 574, 574)
                        .addComponent(SubmitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(151, 151, 151))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lblTitleProd1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(BackBTN1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(TypeOfComplain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(SubmitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTN1ActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
        mainpage.invalidate();
        mainpage.validate();
    }//GEN-LAST:event_BackBTN1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void TypeOfComplainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TypeOfComplainActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_TypeOfComplainActionPerformed

    private void SubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitBtnActionPerformed
        // TODO add your handling code here:
        try {
            // Extract selected values
            String orderId = (String) jComboBox1.getSelectedItem();
            String orderStatus = (String) jTextField1.getText();
            String orderDate = (String) jTextField2.getText();
            String productName = (String) jComboBox4.getSelectedItem();
            String issueType = (String) TypeOfComplain.getSelectedItem();
            String issueDescription = jTextArea1.getText();

            // Basic validation
            if (issueType == null || issueDescription.trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please select an issue type and describe the issue.");
                return;
            }

            // Build document
            Document complaint = new Document("order_id", orderId)
                    .append("product_name", productName)
                    .append("userId", userId)
                    .append("order_status", orderStatus)
                    .append("order_date", orderDate)
                    .append("issue_type", issueType)
                    .append("description", issueDescription)
                    .append("status", "Raised")
                    .append("submitted_on", new java.util.Date()); // Store Date as Date object

            // Insert into DB
            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> complaints = db.getCollection("customerIssue");

            // Check if complaint exists
            Document existingComplaint = complaints.find(new Document("order_id", orderId)).first();

            if (existingComplaint == null) {
                complaints.insertOne(complaint);
                javax.swing.JOptionPane.showMessageDialog(this, "Complaint raised successfully!");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Complaint already exists for this order ID");
            }

            // Clear input
            jTextArea1.setText("");
            TypeOfComplain.setSelectedIndex(0);

        } catch (MongoException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "MongoDB error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Failed to raise complaint. Please try again.");
        }

    }//GEN-LAST:event_SubmitBtnActionPerformed

    private void setupComplainTypes() {
        TypeOfComplain.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "Damaged Product",
            "Delivered Wrong Product",
            "Variation in Size",
            "Delivered Late than expected",
            "Other"
        }));
    }

    private void populateOrderData() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> ordersCollection = db.getCollection("orders");

        ordersList.clear(); // clear previous data
        jComboBox1.removeAllItems(); // order ID
        jTextField1.setText(""); // status
        jTextField2.setText(""); // order date
        jComboBox4.removeAllItems(); // product name

        for (Document order : ordersCollection.find(new Document("isDeleted", false).append("userId", userId))) {
            ordersList.add(order);
            jComboBox1.addItem(order.getObjectId("_id").toString());
        }

        // 🔥 Trigger update after loading
        if (jComboBox1.getItemCount() > 0) {
            jComboBox1.setSelectedIndex(0);
        }
    }

    private void setupComboBoxListeners() {
        jComboBox1.addActionListener(evt -> {
            int index = jComboBox1.getSelectedIndex();
            if (index >= 0 && index < ordersList.size()) {
                Document order = ordersList.get(index);
                updateComboBoxes(order);
            }
        });
    }

    private void updateComboBoxes(Document order) {
        // Reset the fields before updating
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox4.removeAllItems();
      
        // Set the status and date fields
        String status = order.getString("status");
        String date = order.getString("ordered_date");

        if (status != null) {
            jTextField1.setText(status);
        }
        if (date != null) {
            jTextField2.setText(date);
        } 

        // Fetch products related to this order (if product_ids is a list)
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> products = db.getCollection("products");
        List<String> productIds = (List<String>) order.get("product_ids");

        if (productIds != null && !productIds.isEmpty()) {
            for (String pid : productIds) {
                pid = pid.replaceAll("[\\[\\]\"]", "");
                Document product = products.find(new Document("product_id", pid)).first();
                if (product != null) {
                    jComboBox4.addItem(product.getString("product_name"));
                } 
    }}}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN1;
    private javax.swing.JButton SubmitBtn;
    private javax.swing.JComboBox<String> TypeOfComplain;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitleProd1;
    // End of variables declaration//GEN-END:variables
}
