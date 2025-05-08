/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Shari
 */
public class Product {

    private String productId;
    private String productName;
    private String description;
    private String sellerId;
    private String certifierId;
    private String shippingProviderId;
    private String materialsUsed;
    private String carbon;
    private Double price;
    private String category;

    public Product(String productId, String productName, String description, String sellerId, String certifierId, String shippingProviderId, String materialsUsed, String carbon, Double price, String category, int ecoscore) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.sellerId = sellerId;
        this.certifierId = certifierId;
        this.shippingProviderId = shippingProviderId;
        this.materialsUsed = materialsUsed;
        this.carbon = carbon;
        this.price = price;
        this.category = category;
        this.ecoscore = ecoscore;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

   

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setCarbon(String carbon) {
        this.carbon = carbon;
    }

    public String getCarbon() {
        return carbon;
    }

    public void setMaterialsUsed(String materialsUsed) {
        this.materialsUsed = materialsUsed;
    }

    public String getMaterialsUsed() {
        return materialsUsed;
    }
    private int ecoscore;

    public Product() {
    }

    public Product(String productId, String productName, String description, String sellerId, String certifierId, String shippingProviderId, int ecoscore) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.sellerId = sellerId;
        this.certifierId = certifierId;
        this.shippingProviderId = shippingProviderId;
        this.ecoscore = ecoscore;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getCertifierId() {
        return certifierId;
    }

    public void setCertifierId(String certifierId) {
        this.certifierId = certifierId;
    }

    public String getShippingProviderId() {
        return shippingProviderId;
    }

    public void setShippingProviderId(String shippingProviderId) {
        this.shippingProviderId = shippingProviderId;
    }

    public int getEcoscore() {
        return ecoscore;
    }

    public void setEcoscore(int ecoscore) {
        this.ecoscore = ecoscore;
    }
}
