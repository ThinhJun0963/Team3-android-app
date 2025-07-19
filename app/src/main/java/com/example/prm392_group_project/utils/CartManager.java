package com.example.prm392_group_project.utils;

import com.example.prm392_group_project.models.CartItemDTO;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private final List<CartItemDTO> cartItems = new ArrayList<>();

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(CartItemDTO item) {
        for (CartItemDTO existing : cartItems) {
            if (existing.getProductId().equals(item.getProductId())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }

    public void updateQuantity(Long productId, int newQuantity) {
        for (CartItemDTO item : cartItems) {
            if (item.getProductId().equals(productId)) {
                if (newQuantity <= 0) {
                    removeFromCart(productId);
                } else {
                    item.setQuantity(newQuantity);
                }
                break;
            }
        }
    }

    public int getQuantity(Long productId) {
        for (CartItemDTO item : cartItems) {
            if (item.getProductId().equals(productId)) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    public List<CartItemDTO> getItems() {
        return new ArrayList<>(cartItems);
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItemDTO item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
