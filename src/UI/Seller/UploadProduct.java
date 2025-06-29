/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Seller;

import Model.Product;
import Repository.MongoDBConnection;
import UI.Buyer.BuyerSplitPage;
import UI.MainJFrame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.bson.Document;

/**
 *
 * @author Shari
 */
public class UploadProduct extends javax.swing.JPanel {

    MainJFrame mainpage;
    String roleId = null;
    String userId = null;

    /**
     * Creates new form SellerDashboard
     */
    public UploadProduct(MainJFrame mainpage, String roleId, String userId) {
        initComponents();
        populateCategories();
        this.mainpage = mainpage;
        this.roleId = roleId;
        this.userId = userId;
//Panel Styling
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(232, 245, 253));
        
        //Styling Variables
        Color ecoBlueBg = new Color(232, 245, 253);      // soft blue bg
        Color navBlue = new Color(6, 22, 51);            // title/nav text
        Color primaryBlue = new Color(33, 150, 243);     // button blue
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        Font subtitleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

//Title and Back Button Panel
        JLabel lblMainTitle = new JLabel("Seller Dashboard", JLabel.CENTER);
        lblMainTitle.setFont(titleFont);
        lblMainTitle.setForeground(navBlue);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground((primaryBlue.darker()));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backBtn.addActionListener(e -> {
            mainpage.setContentPane(new SellerSplitPage(mainpage));
            mainpage.revalidate();
            mainpage.repaint();
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(backBtn, BorderLayout.WEST);

//Subtitle
        JLabel lblSubtitle = new JLabel("Upload Product", SwingConstants.CENTER);
        lblSubtitle.setFont(subtitleFont);

        JPanel subtitlePanel = new JPanel(new BorderLayout());
        subtitlePanel.setBackground(getBackground());
        subtitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        subtitlePanel.add(lblSubtitle, BorderLayout.CENTER);

//Form Panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ecoBlueBg);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

//Helper to Add Label + Field
        int row = 0;

        String[] labels = {
            "Product Name", "Product Description", "Category", "Price",
            "Material Used", "Production Emission (CO2 in KG)", "Manufacturing Location"
        };

        javax.swing.JComponent[] fields = {
            txtProdName, ScrollPane, CategoryList, txtPrice1,
            txtMaterial, txtProdEmission, txtManf
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = row;
            JLabel lbl = new JLabel(labels[i] + ":");
            lbl.setFont(labelFont);
            lbl.setForeground(navBlue);
            formPanel.add(lbl, gbc);

            gbc.gridx = 1;
            if (fields[i] instanceof javax.swing.JComboBox) {
                fields[i].setFont(inputFont);
                fields[i].setBackground(Color.WHITE);
            } else if (fields[i] instanceof javax.swing.JScrollPane) {
                ((JScrollPane) fields[i]).setPreferredSize(new Dimension(250, 100));
            } else {
                fields[i].setFont(inputFont);
                fields[i].setPreferredSize(new Dimension(250, 35));
                fields[i].setBackground(Color.WHITE);
            }
            formPanel.add(fields[i], gbc);
            row++;
        }

//Button Panel
        btnSubmit.setFont(buttonFont);
        btnSubmit.setBackground(primaryBlue);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(ecoBlueBg);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.add(btnSubmit);

//Final Layout Assembly 
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

        lblManf = new javax.swing.JLabel();
        lblTitleUpload = new javax.swing.JLabel();
        lblProdName = new javax.swing.JLabel();
        lblProdDesc = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblMaterial = new javax.swing.JLabel();
        lblProdEmission = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        txtProdName = new javax.swing.JTextField();
        ScrollPane = new javax.swing.JScrollPane();
        txtProdDesc = new javax.swing.JTextArea();
        txtMaterial = new javax.swing.JTextField();
        txtProdEmission = new javax.swing.JTextField();
        txtManf = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        BackBTN = new javax.swing.JButton();
        txtPrice1 = new javax.swing.JTextField();
        lblPrice1 = new javax.swing.JLabel();
        CategoryList = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1400, 800));

        lblManf.setText("Manufacturing Location ");

        lblTitleUpload.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleUpload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleUpload.setText("Upload Product ");

        lblProdName.setText("Product Name");

        lblProdDesc.setText("Product Description ");

        lblPrice.setText("Category");

        lblMaterial.setText("Material Used");

        lblProdEmission.setText("Production Emission (CO2 in KG)");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        txtProdName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdNameActionPerformed(evt);
            }
        });

        txtProdDesc.setColumns(20);
        txtProdDesc.setRows(5);
        ScrollPane.setViewportView(txtProdDesc);

        txtMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaterialActionPerformed(evt);
            }
        });

        txtProdEmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdEmissionActionPerformed(evt);
            }
        });

        txtManf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtManfActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Seller Dashboard");

        BackBTN.setText("Back");
        BackBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTNActionPerformed(evt);
            }
        });

        txtPrice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrice1ActionPerformed(evt);
            }
        });

        lblPrice1.setText("Price");

        CategoryList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CategoryList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoryListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblProdEmission, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                        .addComponent(lblManf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPrice1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(156, 156, 156)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtProdEmission, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                                        .addComponent(txtMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                                        .addComponent(txtManf, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                                        .addComponent(txtPrice1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                                        .addComponent(CategoryList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblProdName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblProdDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                                    .addGap(156, 156, 156)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtProdName, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(lblTitleUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(153, 153, 153))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitleUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProdName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProdName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProdDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoryList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProdEmission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProdEmission, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblManf, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtManf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSubmit)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTNActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new SellerSplitPage(mainpage));
        mainpage.invalidate();
        mainpage.validate();
    }//GEN-LAST:event_BackBTNActionPerformed

    private String generateUniqueUserId() {
        return "P" + System.currentTimeMillis();
    }
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        Product pr = new Product();
        pr.setProductId(generateUniqueUserId());
        pr.setProductName(txtProdName.getText());
        pr.setDescription(txtProdDesc.getText());
        pr.setCategory((String) CategoryList.getSelectedItem());
        pr.setSellerId(userId);
        pr.setCertifierId(null);
        pr.setShippingProviderId("null");
        pr.setMaterialsUsed(txtMaterial.getText());
        pr.setCarbon(txtProdEmission.getText());
        pr.setPrice(Double.parseDouble(txtPrice1.getText()));
        pr.setIs_audit(false);
        pr.setEcoscore(0);

        MongoDatabase db = MongoDBConnection.getDatabase();
        if (db != null) {
            MongoCollection<Document> productCollection = db.getCollection("products");

            Document productDoc = new Document("product_id", pr.getProductId())
                    .append("product_name", pr.getProductName())
                    .append("description", pr.getDescription())
                    .append("category", pr.getCategory())
                    .append("seller_id", pr.getSellerId())
                    .append("certifier_id", "")
                    .append("shipping_provider_id", "")
                    .append("materials_used", pr.getMaterialsUsed())
                    .append("price", pr.getPrice())
                    .append("carbon_score", Integer.valueOf(pr.getCarbon()))
                    .append("ecoscore", pr.getEcoscore());

            productCollection.insertOne(productDoc);

            javax.swing.JOptionPane.showMessageDialog(this, "Product uploaded successfully!");

            // Optionally clear form after saving
            txtProdName.setText("");
            txtProdDesc.setText("");
            txtMaterial.setText("");
            txtPrice1.setText("");
            txtProdEmission.setText("");
            txtManf.setText("");

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Database connection failed. Product not uploaded.");
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void txtProdNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdNameActionPerformed

    private void txtMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaterialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaterialActionPerformed

    private void txtProdEmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdEmissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdEmissionActionPerformed

    private void txtManfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtManfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtManfActionPerformed

    private void txtPrice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrice1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrice1ActionPerformed

    private void CategoryListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CategoryListActionPerformed
        // TODO add your handling code here:
        String selectedCategory = (String) CategoryList.getSelectedItem();
        System.out.println("Selected category: " + selectedCategory);
    }//GEN-LAST:event_CategoryListActionPerformed

    private void populateCategories() {
        CategoryList.removeAllItems(); // Clear previous items

        // You can fetch these from a database or keep them static
        String[] categories = {
            "Select Category", "Phone", "Computer", "Furniture", "Appliances",
            "Clothing", "Books", "Toys", "Kitchenware"
        };

        for (String category : categories) {
            CategoryList.addItem(category);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN;
    private javax.swing.JComboBox<String> CategoryList;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel lblManf;
    private javax.swing.JLabel lblMaterial;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblPrice1;
    private javax.swing.JLabel lblProdDesc;
    private javax.swing.JLabel lblProdEmission;
    private javax.swing.JLabel lblProdName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleUpload;
    private javax.swing.JTextField txtManf;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtPrice1;
    private javax.swing.JTextArea txtProdDesc;
    private javax.swing.JTextField txtProdEmission;
    private javax.swing.JTextField txtProdName;
    // End of variables declaration//GEN-END:variables
}
