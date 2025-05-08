package Model;

import java.util.List;

public class CartItem {

    private List<String> productId;
    private String productName;
    private double price;
    private int quantity;
    private int ecoScore;

    public CartItem(List<String> productId, String productName, double price, int ecoScore, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.ecoScore = ecoScore;
    }

    public void setProductId(List<String> productId) {
        this.productId = productId;
    }

    public List<String> getProductId() {
        return productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setEcoScore(int ecoScore) {
        this.ecoScore = ecoScore;
    }

    public int getEcoScore() {
        return ecoScore;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return price * quantity;
    }
}
