## Controller → DAO calls (current implementation)

```mermaid
flowchart TB
  subgraph Controllers[Controllers (Servlets)]
    Login[LoginServlet /login]
    Register[RegisterServlet /register]
    Logout[LogoutServlet /logout]
    Home[HomeServlet /home]
    Products[ProductServlet /products]
    Details[ProductDetailsServlet /product-details]
    Cart[CartServlet /cart]
    Add[AddToCartServlet /cart/add]
    Update[UpdateCartQuantityServlet /cart/update]
    Remove[RemoveCartItemServlet /cart/remove]
    Checkout[CheckoutServlet /checkout]
    Place[PlaceOrderServlet /place-order]
    Orders[OrdersServlet /orders]
  end

  subgraph DAOs[DAOs]
    UserDAO[UserDAO]
    CategoryDAO[CategoryDAO]
    ProductDAO[ProductDAO]
    VariantDAO[ProductVariantDAO]
    CartDAO[CartDAO]
    CartItemDAO[CartItemDAO]
    OrderDAO[OrderDAO]
    OrderItemDAO[OrderItemDAO]
  end

  Login --> UserDAO
  Register --> UserDAO
  Home --> CategoryDAO
  Home --> ProductDAO
  Products --> ProductDAO
  Products --> CategoryDAO
  Details --> ProductDAO
  Details --> VariantDAO
  Cart --> CartDAO
  Cart --> CartItemDAO
  Add --> CartDAO
  Add --> CartItemDAO
  Update --> CartItemDAO
  Remove --> CartItemDAO
  Checkout --> CartDAO
  Checkout --> CartItemDAO
  Place --> CartDAO
  Place --> CartItemDAO
  Place --> OrderDAO
  Place --> OrderItemDAO
  Orders --> OrderDAO
```

## DAO methods exercised per flow (high signal)

- **Login**
  - `UserDAO.loginUser(email, password)`
- **Register**
  - `UserDAO.emailExists(email)`
  - `UserDAO.registerUser(user)`
- **Home**
  - `CategoryDAO.getAllCategories()`
  - `ProductDAO.getLatestProducts(limit)`
- **Product listing**
  - `ProductDAO.getAllProducts()` or `ProductDAO.getProductsByCategory(String category)`
  - `CategoryDAO.getAllCategories()`
- **Product details**
  - `ProductDAO.getProductById(productId)`
  - `ProductVariantDAO.getVariantsByProductId(productId)`
- **Cart view**
  - `CartDAO.getCartByUserId(userId)`
  - `CartItemDAO.getCartItemsByCartId(cartId)`
- **Add to cart**
  - `CartDAO.getCartByUserId(userId)` / `CartDAO.addCart(cart)`
  - `CartItemDAO.addCartItem(item)`
- **Update quantity**
  - `CartItemDAO.getCartItemById(cartItemId)`
  - `CartItemDAO.updateCartItemQuantity(cartItemId, quantity)`
- **Remove item**
  - `CartItemDAO.removeCartItem(cartItemId)`
- **Checkout (screen)**
  - `CartDAO.getCartByUserId(userId)`
  - `CartItemDAO.getCartItemsByCartId(cartId)`
  - `CartItemDAO.getCartTotal(cartId)`
- **Place order**
  - `OrderDAO.placeOrder(order)` (sets generated `orderId`)
  - `OrderItemDAO.addOrderItem(item)` per cart line
  - `CartItemDAO.clearCart(cartId)`
- **Orders**
  - `OrderDAO.getOrdersByUserId(userId)`

