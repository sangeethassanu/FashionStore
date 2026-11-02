package com.fashionstore.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;

public interface CartItemDAO {

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItemQuantity(int cartItemId, int quantity);

    boolean updateCartItemQuantityByCartAndVariant(int cartId, int variantId, int quantity);

    boolean removeCartItem(int cartItemId);

    boolean removeCartItemByCartAndVariant(int cartId, int variantId);

    CartItem getCartItemById(int cartItemId);

    CartItem getCartItemByCartIdAndVariantId(int cartId, int variantId);

    List<CartItem> getCartItemsByCartId(Cart cartId);

    boolean clearCart(int cartId);

    int getCartItemCount(int cartId);

    BigDecimal getCartTotal(int cartId);

	List<CartItem> getCartItemsByCartId(int cartId) throws Exception;
}
