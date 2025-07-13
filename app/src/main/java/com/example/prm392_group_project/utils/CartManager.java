package com.example.prm392_group_project.utils;

import com.example.prm392_group_project.models.CartItemDTO;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<CartItemDTO> cartItems = new ArrayList<>();

    public static void addToCart(CartItemDTO item) {
        for (CartItemDTO existing : cartItems) {
            if (existing.getProductId().equals(item.getProductId())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public static void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }

    public static List<CartItemDTO> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public static double getTotalPrice() {
        double total = 0;
        for (CartItemDTO item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
