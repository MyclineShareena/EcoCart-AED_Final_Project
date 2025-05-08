/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Shari
 */

public class Enterprise {
    private String enterpriseId;
    private String enterpriseName;

    // Constructors
    public Enterprise() {}

    public Enterprise(String enterpriseId, String enterpriseName) {
        this.enterpriseId = enterpriseId;
        this.enterpriseName = enterpriseName;
    }

    // Getters and Setters
    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}