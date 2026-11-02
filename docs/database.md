## Database overview

- **Database name**: `fashion_store` (from `com.fashionstore.util.DBConnection`)
- **Access pattern**: direct JDBC via `DBConnection.getConnection()` in each `*DAOImpl`

## ER diagram (inferred from DAO SQL)

```mermaid
erDiagram
  USERS ||--o| CART : has
  CART ||--o{ CART_ITEMS : contains

  CATEGORIES ||--o{ PRODUCTS : groups
  PRODUCTS ||--o{ PRODUCT_VARIANTS : has
  PRODUCT_VARIANTS ||--o{ CART_ITEMS : chosen_as

  USERS ||--o{ ORDERS : places
  ORDERS ||--o{ ORDER_ITEMS : contains
  PRODUCT_VARIANTS ||--o{ ORDER_ITEMS : ordered_as

  USERS {
    int user_id PK
    string full_name
    string email
    string phone
    string password
    string address_line1
    string address_line2
    string city
    string state
    string pincode
    string country
    timestamp created_at
  }

  CATEGORIES {
    int category_id PK
    string category_name
    string description
  }

  PRODUCTS {
    int product_id PK
    int category_id FK
    string product_name
    string brand
    string description
    decimal price
    string image_url
    boolean is_active
    timestamp created_at
  }

  PRODUCT_VARIANTS {
    int variant_id PK
    int product_id FK
    string size
    string color
    int stock_quantity
  }

  CART {
    int cart_id PK
    int user_id FK
    timestamp created_at
  }

  CART_ITEMS {
    int cart_item_id PK
    int cart_id FK
    int variant_id FK
    int quantity
  }

  ORDERS {
    int order_id PK
    int user_id FK
    timestamp order_date
    decimal total_amount
    string payment_method
    string order_status
    string delivery_name
    string delivery_phone
    string delivery_address_line1
    string delivery_city
    string delivery_state
    string delivery_pincode
    string delivery_country
  }

  ORDER_ITEMS {
    int order_item_id PK
    int order_id FK
    int variant_id FK
    int quantity
    decimal price
  }
```

## Table ↔ Java mapping

```mermaid
flowchart LR
  users[(users)] --> User[com.fashionstore.model.User]
  categories[(categories)] --> Category[com.fashionstore.model.Category]
  products[(products)] --> Product[com.fashionstore.model.Product]
  product_variants[(product_variants)] --> ProductVariant[com.fashionstore.model.ProductVariant]
  cart[(cart)] --> Cart[com.fashionstore.model.Cart]
  cart_items[(cart_items)] --> CartItem[com.fashionstore.model.CartItem]
  orders[(orders)] --> Order[com.fashionstore.model.Order]
  order_items[(order_items)] --> OrderItem[com.fashionstore.model.OrderItem]
```

## Table ↔ DAO mapping (where SQL lives)

```mermaid
flowchart TB
  users[(users)] --> UserDAOImpl[com.fashionstore.dao.impl.UserDAOImpl]
  categories[(categories)] --> CategoryDAOImpl[com.fashionstore.dao.impl.CategoryDAOImpl]
  products[(products)] --> ProductDAOImpl[com.fashionstore.dao.impl.ProductDAOImpl]
  product_variants[(product_variants)] --> ProductVariantDAOImpl[com.fashionstore.dao.impl.ProductVariantDAOImpl]
  cart[(cart)] --> CartDAOImpl[com.fashionstore.dao.impl.CartDAOImpl]
  cart_items[(cart_items)] --> CartItemDAOImpl[com.fashionstore.dao.impl.CartItemDAOImpl]
  orders[(orders)] --> OrderDAOImpl[com.fashionstore.dao.impl.OrderDAOImpl]
  order_items[(order_items)] --> OrderItemDAOImpl[com.fashionstore.dao.impl.OrderItemDAOImpl]
```

## Notes / constraints visible in code

- **Cart totals**: computed by `cart_items → product_variants → products` join (see `CartItemDAOImpl.getCartTotal`).
- **Order delivery fields**: `OrderDAOImpl.placeOrder` currently hard-codes city/state/pincode/country to `Chennai / Tamil Nadu / 600001 / India` even though the `Order` model includes those fields.

