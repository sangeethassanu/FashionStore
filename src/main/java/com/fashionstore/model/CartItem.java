package com.fashionstore.model;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int variantId;
    private int quantity;

    // PRODUCT DETAILS
    private String productName;
    private double price;
    private String imageUrl;
    private int productId;
    public CartItem() {
    }

    public CartItem(int cartItemId,
                    int cartId,
                    int variantId,
                    int quantity) {

        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.variantId = variantId;
        this.quantity = quantity;
    }

    // ================= CART ITEM ID =================

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    // ================= CART ID =================

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    // ================= VARIANT ID =================

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    // ================= QUANTITY =================

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ================= PRODUCT NAME =================

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // ================= PRICE =================

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ================= IMAGE =================

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}