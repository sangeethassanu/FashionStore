## Application flows (sequence diagrams)

### Login (`/login`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as LoginServlet
  participant D as UserDAOImpl
  participant DB as MySQL

  U->>B: Open /login (GET)
  B->>C: GET /login
  C-->>B: Forward to login.jsp

  U->>B: Submit email/password (POST)
  B->>C: POST /login
  C->>D: loginUser(email, password)
  D->>DB: SELECT * FROM users WHERE email=? AND password=?
  DB-->>D: row / none
  D-->>C: User / null
  alt success
    C->>C: create HttpSession + set loggedUser
    C-->>B: Redirect /products
  else failure
    C-->>B: Forward login.jsp (error)
  end
```

### Register (`/register`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as RegisterServlet
  participant D as UserDAOImpl
  participant DB as MySQL

  U->>B: Open /register (GET)
  B->>C: GET /register
  C-->>B: Forward register.jsp

  U->>B: Submit registration (POST)
  B->>C: POST /register
  C->>D: emailExists(email)
  D->>DB: SELECT 1 FROM users WHERE email=?
  DB-->>D: exists / not
  D-->>C: boolean
  alt email exists
    C-->>B: Forward register.jsp (error)
  else new email
    C->>D: registerUser(user)
    D->>DB: INSERT INTO users (...)
    DB-->>D: rows
    D-->>C: boolean
    alt success
      C-->>B: Redirect /login
    else failure
      C-->>B: Forward register.jsp (error)
    end
  end
```

### Browse products (`/products`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as ProductServlet
  participant P as ProductDAOImpl
  participant Cat as CategoryDAOImpl
  participant DB as MySQL

  U->>B: Open /products (GET)
  B->>C: GET /products?filters...
  alt category param present (string)
    C->>P: getProductsByCategory(categoryName)
    P->>DB: SELECT * FROM products WHERE category_id=?
    DB-->>P: rows
    P-->>C: List<Product>
  else no category param
    C->>P: getAllProducts()
    P->>DB: SELECT * FROM products ORDER BY product_id DESC
    DB-->>P: rows
    P-->>C: List<Product>
  end
  C->>C: apply in-memory filters/sort (keyword, min/max, sort)
  C->>Cat: getAllCategories()
  Cat->>DB: SELECT * FROM categories ORDER BY category_id DESC
  DB-->>Cat: rows
  Cat-->>C: List<Category>
  C-->>B: Forward products.jsp
```

### Product details (`/product-details?productId=...`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as ProductDetailsServlet
  participant P as ProductDAOImpl
  participant V as ProductVariantDAOImpl
  participant DB as MySQL

  U->>B: Open product details (GET)
  B->>C: GET /product-details?productId=123
  C->>P: getProductById(123)
  P->>DB: SELECT * FROM products WHERE product_id=?
  DB-->>P: row/none
  P-->>C: Product/null
  alt product found
    C->>V: getVariantsByProductId(123)
    V->>DB: SELECT * FROM product_variants WHERE product_id=?
    DB-->>V: rows
    V-->>C: List<ProductVariant>
    C-->>B: Forward product-details.jsp
  else not found
    C-->>B: Redirect /products
  end
```

### Add to cart (`/cart/add?productId=...&variantId=...`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as AddToCartServlet
  participant Cart as CartDAOImpl
  participant Item as CartItemDAOImpl
  participant DB as MySQL

  U->>B: Click "Add to cart"
  B->>C: GET /cart/add?productId=...&variantId=...
  C->>C: session check (loggedUser)
  C->>Cart: getCartByUserId(userId)
  Cart->>DB: SELECT * FROM cart WHERE user_id=?
  DB-->>Cart: row/none
  Cart-->>C: Cart/null
  alt no cart
    C->>Cart: addCart(userId)
    Cart->>DB: INSERT INTO cart(user_id, created_at) VALUES(...)
    DB-->>Cart: generated cart_id
    Cart-->>C: cartId
  end
  C->>Item: addCartItem(cartId, variantId, qty=1)
  Item->>DB: INSERT INTO cart_items(cart_id, variant_id, quantity) VALUES(...)
  DB-->>Item: rows
  Item-->>C: boolean
  C-->>B: Redirect /product-details?productId=...
```

### View cart (`/cart`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as CartServlet
  participant Cart as CartDAOImpl
  participant Item as CartItemDAOImpl
  participant DB as MySQL

  U->>B: Open /cart
  B->>C: GET /cart
  C->>Cart: getCartByUserId(userId)
  Cart->>DB: SELECT * FROM cart WHERE user_id=?
  DB-->>Cart: row/none
  Cart-->>C: Cart/null
  alt cart exists
    C->>Item: getCartItemsByCartId(cartId)
    Item->>DB: SELECT ... FROM cart_items JOIN product_variants JOIN products WHERE cart_id=?
    DB-->>Item: rows
    Item-->>C: List<CartItem>
    C-->>B: Forward cart.jsp
  else empty
    C-->>B: Forward cart.jsp (no items)
  end
```

### Update cart quantity (`/cart/update?cartItemId=...&action=increase|decrease`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as UpdateCartQuantityServlet
  participant Item as CartItemDAOImpl
  participant DB as MySQL

  U->>B: Click +/-
  B->>C: GET /cart/update?cartItemId=..&action=..
  C->>Item: getCartItemById(cartItemId)
  Item->>DB: SELECT * FROM cart_items WHERE cart_item_id=?
  DB-->>Item: row/none
  Item-->>C: CartItem/null
  alt item exists
    C->>C: compute new quantity (>=1)
    C->>Item: updateCartItemQuantity(cartItemId, quantity)
    Item->>DB: UPDATE cart_items SET quantity=? WHERE cart_item_id=?
    DB-->>Item: rows
  end
  C-->>B: Redirect /cart
```

### Checkout screen (`/checkout`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as CheckoutServlet
  participant Cart as CartDAOImpl
  participant Item as CartItemDAOImpl
  participant DB as MySQL

  U->>B: Open /checkout
  B->>C: GET /checkout
  C->>Cart: getCartByUserId(userId)
  Cart->>DB: SELECT * FROM cart WHERE user_id=?
  DB-->>Cart: row/none
  Cart-->>C: Cart/null
  alt no cart
    C-->>B: Redirect /cart
  else cart exists
    C->>Item: getCartItemsByCartId(cartId)
    Item->>DB: SELECT ... cart_items JOIN ... WHERE cart_id=?
    DB-->>Item: rows
    Item-->>C: List<CartItem>
    C->>Item: getCartTotal(cartId)
    Item->>DB: SELECT SUM(p.price * ci.quantity) ...
    DB-->>Item: total
    Item-->>C: BigDecimal
    C-->>B: Forward checkout.jsp
  end
```

### Place order (`/place-order`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as PlaceOrderServlet
  participant Cart as CartDAOImpl
  participant Item as CartItemDAOImpl
  participant O as OrderDAOImpl
  participant OI as OrderItemDAOImpl
  participant DB as MySQL

  U->>B: Submit place-order form
  B->>C: POST /place-order
  C->>Cart: getCartByUserId(userId)
  Cart->>DB: SELECT * FROM cart WHERE user_id=?
  DB-->>Cart: row/none
  Cart-->>C: Cart/null
  alt no cart
    C-->>B: Redirect /cart
  else cart exists
    C->>Item: getCartItemsByCartId(cartId)
    Item->>DB: SELECT ... WHERE cart_id=?
    DB-->>Item: rows
    Item-->>C: List<CartItem>
    C->>Item: getCartTotal(cartId)
    Item->>DB: SELECT SUM(...) WHERE cart_id=?
    DB-->>Item: total
    Item-->>C: BigDecimal
    C->>O: placeOrder(order)
    O->>DB: INSERT INTO orders (...)
    DB-->>O: generated order_id
    O-->>C: orderId set on Order
    loop each cart line
      C->>OI: addOrderItem(orderId, variantId, qty, price)
      OI->>DB: INSERT INTO order_items(...)
      DB-->>OI: rows
    end
    C->>Item: clearCart(cartId)
    Item->>DB: DELETE FROM cart_items WHERE cart_id=?
    DB-->>Item: rows
    C-->>B: Forward order-success.jsp
  end
```

### Orders list (`/orders`)

```mermaid
sequenceDiagram
  autonumber
  actor U as User
  participant B as Browser
  participant C as OrdersServlet
  participant O as OrderDAOImpl
  participant DB as MySQL

  U->>B: Open /orders
  B->>C: GET /orders
  C->>O: getOrdersByUserId(userId)
  O->>DB: SELECT * FROM orders WHERE user_id=? ORDER BY order_id DESC
  DB-->>O: rows
  O-->>C: List<Order>
  C-->>B: Forward orders.jsp
```

