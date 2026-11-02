package com.fashionstore.dao;

import com.fashionstore.model.Cart;

public interface CartDAO {

    boolean createCart(int userId);

    Cart getCartById(int cartId);
    
    int addCart(Cart cart) throws Exception;

    Cart getCartByUserId(int userId) throws Exception;

    Cart getOrCreateCartByUserId(int userId)throws Exception;

    boolean deleteCart(int cartId);

    boolean cartExistsByUserId(int userId);
}
