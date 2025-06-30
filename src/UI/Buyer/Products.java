/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Buyer;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import Repository.MongoDBConnection;
import Service.ButtonRenderer;
import Service.CartButtonEditor;
import UI.MainJFrame;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import org.bson.conversions.Bson;

/**
 *
 * @author Shari
 */
public class Products extends javax.swing.JPanel {

    MainJFrame mainpage;
    String userId;
    private boolean isInitializing = true; // Add flag to prevent ComboBox events during initialization

    /**
     * Creates new form Products
     */
    public Products(MainJFrame mainpage, String userId) {
        this.mainpage = mainpage;
        this.userId = userId;
        this.isInitializing = true; // Set flag

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
        BackBTN.addActionListener(evt -> {
            mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
            mainpage.invalidate();
            mainpage.validate();
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(232, 245, 233));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(BackBTN, BorderLayout.WEST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(232, 245, 233));
        northPanel.add(titlePanel, BorderLayout.NORTH);

        // === Search Panel ===
        lblSupplier = new JLabel("Supplier:");
        lblSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        cmbSupplier = new JComboBox<>();
        cmbSupplier.setPreferredSize(new Dimension(150, 25));
        cmbSupplier.addActionListener(this::cmbSupplierActionPerformed);

        txtSearch = new JTextField(20);

        btnSearchProduct = new JButton("Search Product");
        btnSearchProduct.setBackground(new Color(76, 175, 80));
        btnSearchProduct.setForeground(Color.WHITE);
        btnSearchProduct.setFocusPainted(false);
        btnSearchProduct.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnSearchProduct.addActionListener(this::btnSearchProductActionPerformed);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(232, 245, 233));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        searchPanel.add(lblSupplier);
        searchPanel.add(cmbSupplier);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearchProduct);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        // === Center Panel: Product Page + Table ===
        lblTitleProd = new JLabel("Product Page", SwingConstants.CENTER);
        lblTitleProd.setFont(new Font("Segoe UI", Font.BOLD, 18));

        jTable1 = new JTable();
        jScrollPane1 = new JScrollPane(jTable1);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(232, 245, 233));
        centerPanel.add(lblTitleProd, BorderLayout.NORTH);
        centerPanel.add(jScrollPane1, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // === Table Styling + Data ===
        setupTableStyle(jTable1);
        populateTable();
        populateSupplierComboBox();

        this.isInitializing = false; // Clear flag after initialization
    }

    // Helper methods for null-safe data retrieval
    private double getSafeDouble(Document doc, String fieldName) {
        Double value = doc.getDouble(fieldName);
        return (value != null) ? value : 0.0;
    }

    private int getSafeInteger(Document doc, String fieldName) {
        Integer value = doc.getInteger(fieldName);
        return (value != null) ? value : 0;
    }

    private String getSafeString(Document doc, String fieldName) {
        String value = doc.getString(fieldName);
        return (value != null) ? value : "";
    }

    private void setupTableStyle(JTable table) {
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Product ID", "Product Name", "Category", "Price($)", "Eco Score", "Seller Name", "Add to Cart"}
        ) {
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        });

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
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? evenRow : oddRow);
                } else {
                    c.setBackground(new Color(165, 214, 167));
                }
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
    // </editor-fold>
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTitle = new javax.swing.JLabel();
        lblTitleProd = new javax.swing.JLabel();
        BackBTN = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnSearchProduct = new javax.swing.JButton();
        lblSupplier = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox();

        setPreferredSize(new java.awt.Dimension(1000, 500));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product Name", "Price", "Quantity", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Buyer Dashboard");

        lblTitleProd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitleProd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleProd.setText("Product Page");

        BackBTN.setText("Back");
        BackBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTNActionPerformed(evt);
            }
        });

        btnSearchProduct.setText("Search Product");
        btnSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProductActionPerformed(evt);
            }
        });

        lblSupplier.setText("Supplier:");

        cmbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(213, 213, 213)
                            .addComponent(lblTitleProd, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(66, 66, 66)
                            .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(375, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(lblSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(29, 29, 29)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(153, 153, 153)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnSearchProduct)
                    .addGap(29, 29, 29)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(lblTitleProd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTNActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new BuyerSplitPage(mainpage, userId));
        mainpage.invalidate();
        mainpage.validate();
    }//GEN-LAST:event_BackBTNActionPerformed

    private void btnSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProductActionPerformed
       try {
            String searchTerm = txtSearch.getText().trim().toLowerCase();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> productCollection = db.getCollection("products");
            MongoCollection<Document> userCollection = db.getCollection("users");

            // Step 1: Get all audited products that match the search
            List<Document> products = new ArrayList<>();
            for (Document doc : productCollection.find(Filters.eq("is_audit", true))) {
                String productName = getSafeString(doc, "product_name");
                if (!productName.isEmpty() && productName.toLowerCase().contains(searchTerm)) {
                    products.add(doc);
                }
            }

            // Step 2: Group by category
            Map<String, List<Document>> groupedByCategory = products.stream()
                    .collect(Collectors.groupingBy(doc -> {
                        String cat = getSafeString(doc, "category");
                        return !cat.isEmpty() ? cat : "Unknown";
                    }));

            List<Document> finalSorted = new ArrayList<>();

            // Step 3: Sort each group and accumulate
            groupedByCategory.keySet().stream().sorted().forEach(category -> {
                List<Document> group = groupedByCategory.get(category);

                List<Document> approved = group.stream()
                        .filter(p -> Boolean.TRUE.equals(p.getBoolean("is_bid_approved")))
                        .sorted((d1, d2) -> {
                            double val1 = getSafeDouble(d1, "bid_amount");
                            double val2 = getSafeDouble(d2, "bid_amount");
                            return Double.compare(val2, val1); // Descending
                        })
                        .collect(Collectors.toList());

                List<Document> unapproved = group.stream()
                        .filter(p -> !Boolean.TRUE.equals(p.getBoolean("is_bid_approved")))
                        .sorted((d1, d2) -> {
                            String name1 = getSafeString(d1, "product_name");
                            String name2 = getSafeString(d2, "product_name");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());

                approved.addAll(unapproved);
                finalSorted.addAll(approved);
            });

            // Step 4: Populate the table
            for (Document doc : finalSorted) {
                String productId = getSafeString(doc, "product_id");
                String productName = getSafeString(doc, "product_name");
                String category = getSafeString(doc, "category");
                double price = getSafeDouble(doc, "price");
                int ecoScore = getSafeInteger(doc, "ecoscore");
                String sellerId = getSafeString(doc, "seller_id");

                Document seller = userCollection.find(new Document("user_id", sellerId)).first();
                String sellerName = (seller != null) ? getSafeString(seller, "name") : "Unknown";
                if (sellerName.isEmpty()) sellerName = "Unknown";

                model.addRow(new Object[]{
                    productId,
                    productName,
                    category.isEmpty() ? "Unknown" : category,
                    price,
                    ecoScore,
                    sellerName,
                    "Add to Cart"
                });
            }

            // Step 5: Set up Add to Cart column
            if (jTable1.getColumnCount() > 6) {
                TableColumn cartColumn = jTable1.getColumn("Add to Cart");
                cartColumn.setCellRenderer(new ButtonRenderer());
                cartColumn.setCellEditor(new CartButtonEditor(new JCheckBox(), jTable1, userId));

                // Step 6: Hide Product ID column visually, but preserve the data
                TableColumn idCol = jTable1.getColumnModel().getColumn(0);
                idCol.setMinWidth(0);
                idCol.setMaxWidth(0);
                idCol.setPreferredWidth(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error searching products: " + e.getMessage(), 
                "Database Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_btnSearchProductActionPerformed

    private void cmbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSupplierActionPerformed
        // TODO add your handling code here:
        if (isInitializing || cmbSupplier.getSelectedItem() == null) {
            return;
        }
        
        try {
            String selectedSeller = cmbSupplier.getSelectedItem().toString();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear table

            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> productCollection = db.getCollection("products");
            MongoCollection<Document> userCollection = db.getCollection("users");

            List<Document> products = new ArrayList<>();

            // Step 1: Filter by is_audit and selected seller
            for (Document product : productCollection.find(Filters.eq("is_audit", true))) {
                String sellerId = getSafeString(product, "seller_id");
                Document seller = userCollection.find(new Document("user_id", sellerId)).first();
                String sellerName = (seller != null) ? getSafeString(seller, "name") : "Unknown";
                if (sellerName.isEmpty()) sellerName = "Unknown";

                if (selectedSeller.equals("All Suppliers") || sellerName.equals(selectedSeller)) {
                    product.append("resolved_seller_name", sellerName);
                    products.add(product);
                }
            }

            // Step 2: Group by category
            Map<String, List<Document>> groupedByCategory = products.stream()
                    .collect(Collectors.groupingBy(doc -> {
                        String cat = getSafeString(doc, "category");
                        return !cat.isEmpty() ? cat : "Unknown";
                    }));

            List<Document> finalSorted = new ArrayList<>();

            // Step 3: Sort within each category
            groupedByCategory.keySet().stream().sorted().forEach(category -> {
                List<Document> group = groupedByCategory.get(category);

                List<Document> approved = group.stream()
                        .filter(p -> Boolean.TRUE.equals(p.getBoolean("is_bid_approved")))
                        .sorted((d1, d2) -> {
                            double val1 = getSafeDouble(d1, "bid_amount");
                            double val2 = getSafeDouble(d2, "bid_amount");
                            return Double.compare(val2, val1); // Descending
                        })
                        .collect(Collectors.toList());

                List<Document> unapproved = group.stream()
                        .filter(p -> !Boolean.TRUE.equals(p.getBoolean("is_bid_approved")))
                        .sorted((d1, d2) -> {
                            String name1 = getSafeString(d1, "product_name");
                            String name2 = getSafeString(d2, "product_name");
                            return name1.compareToIgnoreCase(name2);
                        }).collect(Collectors.toList());

                approved.addAll(unapproved);
                finalSorted.addAll(approved);
            });

            // Step 4: Populate table
            for (Document doc : finalSorted) {
                String productId = getSafeString(doc, "product_id");
                String productName = getSafeString(doc, "product_name");
                String category = getSafeString(doc, "category");
                double price = getSafeDouble(doc, "price");
                int ecoScore = getSafeInteger(doc, "ecoscore");
                String sellerName = getSafeString(doc, "resolved_seller_name");

                model.addRow(new Object[]{
                    productId,
                    productName,
                    category.isEmpty() ? "Unknown" : category,
                    price,
                    ecoScore,
                    sellerName.isEmpty() ? "Unknown" : sellerName,
                    "Add to Cart"
                });
            }

            // Step 5: Restore renderer and editor
            if (jTable1.getColumnCount() > 6) {
                TableColumn cartColumn = jTable1.getColumn("Add to Cart");
                cartColumn.setCellRenderer(new ButtonRenderer());
                cartColumn.setCellEditor(new CartButtonEditor(new JCheckBox(), jTable1, userId));

                // Hide product ID column visually
                TableColumn idCol = jTable1.getColumnModel().getColumn(0);
                idCol.setMinWidth(0);
                idCol.setMaxWidth(0);
                idCol.setPreferredWidth(0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error filtering products: " + e.getMessage(), 
                "Database Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmbSupplierActionPerformed

    private void populateSupplierComboBox() {
        try {
            cmbSupplier.removeAllItems();
            cmbSupplier.addItem("All Suppliers"); // default option

            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> productCollection = db.getCollection("products");
            MongoCollection<Document> userCollection = db.getCollection("users");

            Set<String> sellerNames = new HashSet<>();

            for (Document product : productCollection.find(new Document("is_audit", true))) {
                String sellerId = getSafeString(product, "seller_id");

                if (!sellerId.isEmpty()) {
                    Document seller = userCollection.find(new Document("user_id", sellerId)).first();
                    if (seller != null) {
                        String sellerName = getSafeString(seller, "name");
                        if (!sellerName.isEmpty() && !sellerNames.contains(sellerName)) {
                            sellerNames.add(sellerName);
                            cmbSupplier.addItem(sellerName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error populating supplier combo box: " + e.getMessage());
        }
    }

    public void populateTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> productCollection = db.getCollection("products");
            MongoCollection<Document> userCollection = db.getCollection("users");

            // Step 1: Get all audited products
            List<Document> products = new ArrayList<>();
            for (Document doc : productCollection.find(Filters.eq("is_audit", true))) {
                String productName = getSafeString(doc, "product_name");
                if (!productName.isEmpty()) {
                    products.add(doc);
                }
            }

            // Step 2: Group by category
            Map<String, List<Document>> groupedByCategory = products.stream()
                    .collect(Collectors.groupingBy(doc -> {
                        String cat = getSafeString(doc, "category");
                        return !cat.isEmpty() ? cat : "Unknown";
                    }));

            List<Document> finalSorted = new ArrayList<>();

            // Step 3: Sort each group and accumulate
            groupedByCategory.keySet().stream().sorted().forEach(category -> {
                List<Document> group = groupedByCategory.get(category);

                List<Document> approved = group.stream()
                        .filter(p -> Boolean.TRUE.equals(p.getBoolean("is_bid_approved")))
                        .sorted((d1, d2) -> {
                            double val1 = getSafeDouble(d1, "bid_amount");
                            double val2 = getSafeDouble(d2, "bid_amount");
                            return Double.compare(val2, val1); // Descending
                        })
                        .collect(Collectors.toList());

                List<Document> unapproved = group.stream()
                        .filter(p -> !Boolean.TRUE.equals(p.getBoolean("is_bid_approved")))
                        .sorted((d1, d2) -> {
                            String name1 = getSafeString(d1, "product_name");
                            String name2 = getSafeString(d2, "product_name");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());

                approved.addAll(unapproved);
                finalSorted.addAll(approved);
            });

            // Step 4: Populate the table
            for (Document doc : finalSorted) {
                String productId = getSafeString(doc, "product_id");
                String productName = getSafeString(doc, "product_name");
                String category = getSafeString(doc, "category");
                double price = getSafeDouble(doc, "price");
                int ecoScore = getSafeInteger(doc, "ecoscore");
                String sellerId = getSafeString(doc, "seller_id");

                Document seller = userCollection.find(new Document("user_id", sellerId)).first();
                String sellerName = (seller != null) ? getSafeString(seller, "name") : "Unknown";
                if (sellerName.isEmpty()) sellerName = "Unknown";

                model.addRow(new Object[]{
                    productId,
                    productName,
                    category.isEmpty() ? "Unknown" : category,
                    price,
                    ecoScore,
                    sellerName,
                    "Add to Cart"
                });
            }

            // Step 5: Set up Add to Cart column
            if (jTable1.getColumnCount() > 6) {
                TableColumn cartColumn = jTable1.getColumn("Add to Cart");
                cartColumn.setCellRenderer(new ButtonRenderer());
                cartColumn.setCellEditor(new CartButtonEditor(new JCheckBox(), jTable1, userId));

                // Step 6: Hide Product ID column visually, but preserve the data
                TableColumn idCol = jTable1.getColumnModel().getColumn(0);
                idCol.setMinWidth(0);
                idCol.setMaxWidth(0);
                idCol.setPreferredWidth(0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error loading products: " + e.getMessage(), 
                "Database Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN;
    private javax.swing.JButton btnSearchProduct;
    private javax.swing.JComboBox cmbSupplier;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleProd;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
