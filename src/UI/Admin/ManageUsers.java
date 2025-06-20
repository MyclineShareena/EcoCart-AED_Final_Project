/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Admin;

import UI.Buyer.BuyerSplitPage;
import UI.MainJFrame;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Shari
 */
public class ManageUsers extends javax.swing.JPanel {

    private MainJFrame mainpage;

    private Map<String, List<String>> enterpriseToOrganizations;
    private Map<String, List<String>> organizationToRoles;
    private List<Document> allEnterprises;
    private List<Document> allOrganizations;
    private List<Document> allRoles;

    /**
     * Creates new form AdminSplitPage
     */
    public ManageUsers(MainJFrame mainpage) {
        initComponents();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(242, 236, 248)); // light lavender purple

        // --- NORTH Panel ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(getBackground());
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        BackBTN = new JButton("Back");
        BackBTN.setBackground(new Color(224, 210, 255));
        BackBTN.setForeground(new Color(55, 30, 100));
        BackBTN.setFocusPainted(false);
        BackBTN.addActionListener(e -> {
            mainpage.setContentPane(new AdminSplitPage(mainpage));
            mainpage.invalidate();
            mainpage.validate();
        });

        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(88, 52, 146));

        topPanel.add(BackBTN, BorderLayout.WEST);
        topPanel.add(header, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER Panel ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(getBackground());

        lblTitle = new JLabel("Manage Users", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(88, 52, 146));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        centerPanel.add(lblTitle, BorderLayout.NORTH);

        jTable1 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"User ID", "User Name", "Is Active", "Is Deleted"}
        ));
        styleTable(jTable1);
        centerPanel.add(new JScrollPane(jTable1), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(getBackground());

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(195, 170, 255));
        btnUpdate.setForeground(Color.BLACK);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(195, 170, 255));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFocusPainted(false);

        actionPanel.add(btnUpdate);
        actionPanel.add(btnDelete);

        centerPanel.add(actionPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // --- SOUTH Panel ---
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.setBackground(getBackground());

        jTable2 = createStyledTable(new String[]{"Enterprise List"});
        jTable4 = createStyledTable(new String[]{"Organization List"});
        jTable3 = createStyledTable(new String[]{"Roles List"});

        bottomPanel.add(new JScrollPane(jTable2));
        bottomPanel.add(new JScrollPane(jTable4));
        bottomPanel.add(new JScrollPane(jTable3));

        add(bottomPanel, BorderLayout.SOUTH);

        populateUserTable();
        initializeDataStructures();
        // setupUI();
        populateEnterpriseTable();
        setupTableSelectionListeners();
    }

    private void initializeDataStructures() {
        enterpriseToOrganizations = new HashMap<>();
        organizationToRoles = new HashMap<>();
        allEnterprises = new ArrayList<>();
        allOrganizations = new ArrayList<>();
        allRoles = new ArrayList<>();
        loadHierarchicalData();
    }

    private void loadHierarchicalData() {
        try {
            MongoDatabase db = Repository.MongoDBConnection.getDatabase();

            // Load enterprises
            MongoCollection<Document> enterpriseCollection = db.getCollection("enterprises");
            for (Document doc : enterpriseCollection.find()) {
                allEnterprises.add(doc);
            }

            // Load organizations
            MongoCollection<Document> organizationCollection = db.getCollection("organizations");
            for (Document doc : organizationCollection.find()) {
                allOrganizations.add(doc);

                // Build enterprise -> organization mapping
                String enterpriseId = doc.getString("enterprise_id");
                String organizationId = doc.getString("organization_id");

                if (enterpriseId != null && organizationId != null) {
                    enterpriseToOrganizations.computeIfAbsent(enterpriseId, k -> new ArrayList<>())
                            .add(organizationId);
                }
            }

            // Load roles
            MongoCollection<Document> rolesCollection = db.getCollection("roles");
            for (Document doc : rolesCollection.find()) {
                allRoles.add(doc);

                // Build organization -> roles mapping
                String organizationId = doc.getString("organization_id");
                String roleId = doc.getString("role_id");

                if (organizationId != null && roleId != null) {
                    organizationToRoles.computeIfAbsent(organizationId, k -> new ArrayList<>())
                            .add(roleId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error loading hierarchical data: " + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupTableSelectionListeners() {
        // Enterprise table selection listener
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = jTable2.getSelectedRow();
                    if (selectedRow >= 0) {
                        String selectedEnterpriseName = jTable2.getValueAt(selectedRow, 0).toString();
                        // Find enterprise ID by name
                        String enterpriseId = findEnterpriseIdByName(selectedEnterpriseName);
                        if (enterpriseId != null) {
                            populateOrganizationTable(enterpriseId);
                            clearRolesTable();
                        }
                    }
                }
            }
        });

        // Organization table selection listener
        jTable4.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = jTable4.getSelectedRow();
                    if (selectedRow >= 0) {
                        String selectedOrganizationName = jTable4.getValueAt(selectedRow, 0).toString();
                        // Find organization ID by name
                        String organizationId = findOrganizationIdByName(selectedOrganizationName);
                        if (organizationId != null) {
                            populateRolesTable(organizationId);
                        }
                    }
                }
            }
        });
    }

    private String findEnterpriseIdByName(String enterpriseName) {
        for (Document enterprise : allEnterprises) {
            String name = enterprise.getString("enterprise_name");
            if (enterpriseName.equals(name)) {
                return enterprise.getString("enterprise_id");
            }
        }
        return null;
    }

    private String findOrganizationIdByName(String organizationName) {
        for (Document organization : allOrganizations) {
            String name = organization.getString("organization_name");
            if (organizationName.equals(name)) {
                return organization.getString("organization_id");
            }
        }
        return null;
    }

    private void clearOrganizationTable() {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);
    }

    private void clearRolesTable() {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
    }

    // Fixed: Create table with proper column structure
    private JTable createStyledTable(String[] columnNames) {
        JTable table = new JTable(new DefaultTableModel(new Object[][]{}, columnNames));
        styleTable(table);
        return table;
    }

    private void populateEnterpriseTable() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        for (Document enterprise : allEnterprises) {
            String enterpriseName = enterprise.getString("enterprise_name");
            if (enterpriseName == null) {
                enterpriseName = "N/A";
            }
            model.addRow(new Object[]{enterpriseName});
        }
    }

    private void populateOrganizationTable(String enterpriseId) {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        List<String> organizationIds = enterpriseToOrganizations.get(enterpriseId);
        if (organizationIds != null) {
            for (String orgId : organizationIds) {
                for (Document org : allOrganizations) {
                    if (orgId.equals(org.getString("organization_id"))) {
                        String organizationName = org.getString("organization_name");
                        if (organizationName == null) {
                            organizationName = "N/A";
                        }
                        model.addRow(new Object[]{organizationName});
                        break;
                    }
                }
            }
        }
    }

    private void populateRolesTable(String organizationId) {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);

        List<String> roleIds = organizationToRoles.get(organizationId);
        if (roleIds != null) {
            for (String roleId : roleIds) {
                for (Document role : allRoles) {
                    if (roleId.equals(role.getString("role_id"))) {
                        String roleName = role.getString("role_name");
                        if (roleName == null) {
                            roleName = "N/A";
                        }
                        model.addRow(new Object[]{roleName});
                        break;
                    }
                }
            }
        }
    }

    private void styleTable(JTable table) {
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(220, 220, 220));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(224, 210, 255));
        header.setForeground(new Color(88, 52, 146));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            final Color evenRow = new Color(242, 235, 255);
            final Color oddRow = Color.WHITE;

            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                c.setBackground(!sel ? (row % 2 == 0 ? evenRow : oddRow) : new Color(210, 190, 255));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        Enterprise = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        Roles = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        Organization = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        BackBTN = new javax.swing.JButton();
        maintitle = new javax.swing.JLabel();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Manage Users");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "User Id ", "User Name", "Is Active ", "Is Deleted"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Enterprise List"
            }
        ));
        Enterprise.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Roles List"
            }
        ));
        Roles.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Organization List"
            }
        ));
        Organization.setViewportView(jTable4);

        btnDelete.setText("Delete");

        BackBTN.setText("Back");
        BackBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBTNActionPerformed(evt);
            }
        });

        maintitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        maintitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maintitle.setText("Admin Dashboard");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(448, 448, 448))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Enterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(169, 169, 169)
                        .addComponent(Organization, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(Roles, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 973, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(269, 269, 269)
                                .addComponent(maintitle, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(227, 227, 227)
                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Enterprise, Organization, Roles});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(BackBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(maintitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(Enterprise, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Roles, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Organization, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Enterprise, Organization, Roles});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDelete, btnUpdate});

    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        String userId = jTable1.getValueAt(selectedRow, 0).toString();
        boolean isActive = Boolean.parseBoolean(jTable1.getValueAt(selectedRow, 2).toString());
        boolean isDeleted = Boolean.parseBoolean(jTable1.getValueAt(selectedRow, 3).toString());

        MongoDatabase db = Repository.MongoDBConnection.getDatabase();
        MongoCollection<Document> userCollection = db.getCollection("users");

        Bson filter = eq("userId", userId);
        Bson updates = new Document("$set", new Document("is_active", isActive).append("is_deleted", isDeleted));
        userCollection.updateOne(filter, updates);

        javax.swing.JOptionPane.showMessageDialog(this, "✅ User updated successfully!");
        populateUserTable();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void BackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBTNActionPerformed
        // TODO add your handling code here:
        mainpage.setContentPane(new AdminSplitPage(mainpage));
        mainpage.invalidate();
        mainpage.validate();
    }//GEN-LAST:event_BackBTNActionPerformed
    private void populateUserTable() {
        MongoDatabase db = Repository.MongoDBConnection.getDatabase();
        MongoCollection<Document> userCollection = db.getCollection("users");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Document doc : userCollection.find()) {
            model.addRow(new Object[]{
                doc.getString("user_id"),
                doc.getString("username"),
                doc.getBoolean("is_active"),
                doc.getBoolean("is_deleted")
            });
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBTN;
    private javax.swing.JScrollPane Enterprise;
    private javax.swing.JScrollPane Organization;
    private javax.swing.JScrollPane Roles;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel maintitle;
    // End of variables declaration//GEN-END:variables
}
