/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Shari
 */

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static List<CartItem> cartItems = new ArrayList<>();

    public static void addToCart(CartItem item) {
        cartItems.add(item);
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
