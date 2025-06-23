/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import Repository.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.bson.Document;

/**
 *
 * @author Shari
 */
public class SignUp extends javax.swing.JPanel {

    MainJFrame mainpage;

    /**
     * Creates new form SignUp
     */
    public SignUp(MainJFrame mainpage) {
        this.mainpage = mainpage;
        setBackground(new Color(13, 32, 57)); // EcoCart dark background
        setLayout(new GridBagLayout()); // Center the card

        // === Main Card Panel ===
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(400, 580)); // Increased height for more fields
        cardPanel.setMaximumSize(new Dimension(400, 580));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100)); // Reduced padding

        // === Header ===
        JLabel lblTitle = new JLabel("EcoCart");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(21, 101, 192));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Sign Up");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Username ===
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(300, 30));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Password ===
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(300, 30));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Name ===
        JLabel lblName = new JLabel("Full Name");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtName = new JTextField();
        txtName.setMaximumSize(new Dimension(300, 30));
        txtName.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Email ===
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(250, 30));
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Mobile ===
        JLabel lblMobile = new JLabel("Mobile Number");
        lblMobile.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMobile.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtMobile = new JTextField();
        txtMobile.setMaximumSize(new Dimension(300, 30));
        txtMobile.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Role ===
        JLabel lblRole = new JLabel("Select Role");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbRole = new JComboBox<>();
        cmbRole.setMaximumSize(new Dimension(300, 30));
        cmbRole.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Buttons ===
        btnSignup = new JButton("Sign Up");
        btnSignup.setBackground(new Color(21, 101, 192));
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFocusPainted(false);
        btnSignup.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSignup.addActionListener(evt -> btnSignupActionPerformed(evt));

        backBTN = new JButton("Back");
        backBTN.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBTN.addActionListener(evt -> backBTNActionPerformed(evt));

        // === Add to cardPanel ===
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createVerticalStrut(4));
        cardPanel.add(lblSub);
        cardPanel.add(Box.createVerticalStrut(15));

        // Username
        cardPanel.add(lblUsername);
        cardPanel.add(txtUsername);
        cardPanel.add(Box.createVerticalStrut(8));

        // Password
        cardPanel.add(lblPassword);
        cardPanel.add(txtPassword);
        cardPanel.add(Box.createVerticalStrut(8));

        // Name (ADDED)
        cardPanel.add(lblName);
        cardPanel.add(txtName);
        cardPanel.add(Box.createVerticalStrut(8));

        // Email (ADDED)
        cardPanel.add(lblEmail);
        cardPanel.add(txtEmail);
        cardPanel.add(Box.createVerticalStrut(8));

        // Mobile (ADDED)
        cardPanel.add(lblMobile);
        cardPanel.add(txtMobile);
        cardPanel.add(Box.createVerticalStrut(8));

        // Role
        cardPanel.add(lblRole);
        cardPanel.add(cmbRole);
        cardPanel.add(Box.createVerticalStrut(15));

        // Buttons
        cardPanel.add(btnSignup);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(backBTN);

        add(cardPanel);

        loadRolesIntoDropdown();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        btnSignup = new javax.swing.JButton();
        lblSignup = new javax.swing.JLabel();
        cmbRole = new javax.swing.JComboBox<>();
        backBTN = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        lblemail = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblPassword2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblemail1 = new javax.swing.JLabel();
        lblemail2 = new javax.swing.JLabel();
        lblemail3 = new javax.swing.JLabel();
        txtMobile = new javax.swing.JTextField();

        btnSignup.setText("Sign Up");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        lblSignup.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSignup.setText("Sign Up");

        cmbRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRoleActionPerformed(evt);
            }
        });

        backBTN.setText("Back");
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });

        lblUsername.setText("Username");

        lblRole.setText("Role");

        lblPassword.setText("Password");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        lblemail.setText("Email");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblemail1.setText("Name");

        lblemail3.setText("Mobile");

        txtMobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMobileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)
                        .addComponent(backBTN)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblemail1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblemail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbRole, 0, 188, Short.MAX_VALUE)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword)
                            .addComponent(txtName)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap(350, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblemail3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMobile))
                            .addComponent(lblemail2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(350, 350, 350))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBTN)
                    .addComponent(lblSignup))
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblemail1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword2)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblemail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblemail2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblemail3)
                    .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnSignup)
                .addGap(61, 61, 61))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String mobile = txtMobile.getText().trim();

        String selectedRoleName = (String) cmbRole.getSelectedItem();

        // Validate all required fields
        if (username.isEmpty() || password.isEmpty() || name.isEmpty()
                || email.isEmpty() || mobile.isEmpty() || selectedRoleName == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please fill all fields and select a role!");
            return;
        }

        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid email address!");
            return;
        }

        // Basic mobile validation (assuming 10 digits)
        if (!mobile.matches("\\d{10}")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit mobile number!");
            return;
        }

        MongoDatabase db = MongoDBConnection.getDatabase();
        if (db != null) {
            MongoCollection<Document> userCollection = db.getCollection("users");
            MongoCollection<Document> roleCollection = db.getCollection("roles");

            // Check if username already exists
            Document existingUser = userCollection.find(new Document("username", username)).first();
            if (existingUser != null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Username already exists! Try another username.");
                return;
            }

            // Check if email already exists
            Document existingEmail = userCollection.find(new Document("email", email)).first();
            if (existingEmail != null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Email already exists! Try another email address.");
                return;
            }

            // Check if mobile number already exists
            Document existingMobile = userCollection.find(new Document("mobile", mobile)).first();
            if (existingMobile != null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Mobile number already exists! Try another mobile number.");
                return;
            }

            // Find role_id based on selected role name
            Document roleQuery = new Document("role_name", selectedRoleName);
            Document roleDoc = roleCollection.find(roleQuery).first();

            if (roleDoc != null) {
                String roleId = roleDoc.getString("role_id");

                // Insert new user with all fields
                Document newUser = new Document("user_id", generateUniqueUserId())
                        .append("username", username)
                        .append("password", password)
                        .append("name", name)
                        .append("email", email)
                        .append("mobile", mobile)
                        .append("role_id", roleId)
                        .append("is_active", true)
                        .append("is_deleted", false)
                        .append("created_date", new java.util.Date());

                userCollection.insertOne(newUser);

                javax.swing.JOptionPane.showMessageDialog(this, "User Registered Successfully!");

                // Clear fields after success
                txtUsername.setText("");
                txtPassword.setText("");
                txtName.setText("");
                txtEmail.setText("");
                txtMobile.setText("");
                cmbRole.setSelectedIndex(0);

                // (Optional) After success, go back to Login page
                mainpage.dispose();
                new MainJFrame().setVisible(true);

            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Selected role does not exist!");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Database connection failed!");
        }
    }//GEN-LAST:event_btnSignupActionPerformed

    private String generateUniqueUserId() {
        return "U" + System.currentTimeMillis();  // Example: U1714398001234
    }
    private void cmbRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRoleActionPerformed

    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed
        // TODO add your handling code here:
        mainpage.role = null;
        mainpage.dispose();
        new MainJFrame().setVisible(true);

    }//GEN-LAST:event_backBTNActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtMobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMobileActionPerformed

    public void loadRolesIntoDropdown() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        if (db != null) {
            MongoCollection<Document> roleCollection = db.getCollection("roles");

            try (MongoCursor<Document> cursor = roleCollection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document roleDoc = cursor.next();
                    String roleName = roleDoc.getString("role_name");
                    if (roleName != null) {
                        cmbRole.addItem(roleName);  // Add role name into dropdown
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading roles: " + e.getMessage());
            }
        } else {
            System.out.println("Database connection failed!");
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBTN;
    private javax.swing.JButton btnSignup;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword2;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSignup;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblemail;
    private javax.swing.JLabel lblemail1;
    private javax.swing.JLabel lblemail2;
    private javax.swing.JLabel lblemail3;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
