## High-level architecture (Servlet MVC)

```mermaid
flowchart TB
  Browser[Browser] -->|HTTP GET/POST| Servlet[Controller: *Servlet (Jakarta)]
  Servlet -->|calls| DAO[DAO: com.fashionstore.dao.*]
  DAO -->|JDBC| DB[(MySQL: fashion_store)]
  DB --> DAO
  DAO --> Servlet
  Servlet -->|request.setAttribute + forward| JSP[View: WEB-INF/views/*.jsp]
  JSP -->|HTML| Browser

  subgraph Code structure
    Servlet
    DAO
    Model[Model: com.fashionstore.model.*]
    Util[Util: DBConnection]
  end

  DAO -->|maps rows ↔ objects| Model
  DAO -->|gets Connection| Util
```



## Main modules (by package)

```mermaid
classDiagram
direction LR

class DBConnection {
  +getConnection() Connection
}

namespace controller {
  class LoginServlet
  class RegisterServlet
  class LogoutServlet
  class HomeServlet
  class ProductServlet
  class ProductDetailsServlet
  class CartServlet
  class AddToCartServlet
  class UpdateCartQuantityServlet
  class RemoveCartItemServlet
  class CheckoutServlet
  class PlaceOrderServlet
  class OrdersServlet
}

namespace dao {
  class UserDAO
  class CategoryDAO
  class ProductDAO
  class ProductVariantDAO
  class CartDAO
  class CartItemDAO
  class OrderDAO
  class OrderItemDAO
}

namespace dao_impl {
  class UserDAOImpl
  class CategoryDAOImpl
  class ProductDAOImpl
  class ProductVariantDAOImpl
  class CartDAOImpl
  class CartItemDAOImpl
  class OrderDAOImpl
  class OrderItemDAOImpl
}

namespace model {
  class User
  class Category
  class Product
  class ProductVariant
  class Cart
  class CartItem
  class Order
  class OrderItem
}

controller.LoginServlet --> dao.UserDAO
controller.RegisterServlet --> dao.UserDAO
controller.HomeServlet --> dao.CategoryDAO
controller.HomeServlet --> dao.ProductDAO
controller.ProductServlet --> dao.ProductDAO
controller.ProductServlet --> dao.CategoryDAO
controller.ProductDetailsServlet --> dao.ProductDAO
controller.ProductDetailsServlet --> dao.ProductVariantDAO
controller.CartServlet --> dao.CartDAO
controller.CartServlet --> dao.CartItemDAO
controller.AddToCartServlet --> dao.CartDAO
controller.AddToCartServlet --> dao.CartItemDAO
controller.UpdateCartQuantityServlet --> dao.CartItemDAO
controller.RemoveCartItemServlet --> dao.CartItemDAO
controller.CheckoutServlet --> dao.CartDAO
controller.CheckoutServlet --> dao.CartItemDAO
controller.PlaceOrderServlet --> dao.CartDAO
controller.PlaceOrderServlet --> dao.CartItemDAO
controller.PlaceOrderServlet --> dao.OrderDAO
controller.PlaceOrderServlet --> dao.OrderItemDAO
controller.OrdersServlet --> dao.OrderDAO

dao.UserDAO <|.. dao_impl.UserDAOImpl
dao.CategoryDAO <|.. dao_impl.CategoryDAOImpl
dao.ProductDAO <|.. dao_impl.ProductDAOImpl
dao.ProductVariantDAO <|.. dao_impl.ProductVariantDAOImpl
dao.CartDAO <|.. dao_impl.CartDAOImpl
dao.CartItemDAO <|.. dao_impl.CartItemDAOImpl
dao.OrderDAO <|.. dao_impl.OrderDAOImpl
dao.OrderItemDAO <|.. dao_impl.OrderItemDAOImpl

dao_impl.UserDAOImpl ..> DBConnection
dao_impl.CategoryDAOImpl ..> DBConnection
dao_impl.ProductDAOImpl ..> DBConnection
dao_impl.ProductVariantDAOImpl ..> DBConnection
dao_impl.CartDAOImpl ..> DBConnection
dao_impl.CartItemDAOImpl ..> DBConnection
dao_impl.OrderDAOImpl ..> DBConnection
dao_impl.OrderItemDAOImpl ..> DBConnection
```



## Request routing

- **Annotation routing**: Most controllers are mapped via `@WebServlet("...")` in `com.fashionstore.controller`.
- `**web.xml` routing**: `LoginServlet` and `RegisterServlet` are also declared/mapped in `src/main/webapp/WEB-INF/lib/web.xml`.

