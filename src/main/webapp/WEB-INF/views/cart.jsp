<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Shopping Cart - Fashion Store</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/cart.css' />">

</head>

<body>

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="cart-page">

        <!-- HEADER -->

        <section class="cart-header">

            <h1>Your Shopping Cart</h1>

            <p>
                Review your selected items before checkout.
            </p>

        </section>

        <!-- CART CONTENT -->

        <section class="cart-wrapper">

            <c:choose>

                <c:when test="${not empty cartItems}">

                    <div class="cart-items">

                        <c:forEach var="item"
                                   items="${cartItems}">

                            <div class="cart-card">

                                <!-- PRODUCT IMAGE -->

                                <div class="cart-image">

                                    <c:choose>

                                        <c:when test="${item.imageUrl.startsWith('http')}">

                                            <img src="${item.imageUrl}"
                                                 alt="${item.productName}" />

                                        </c:when>

                                        <c:otherwise>

                                            <img src="<c:url value='/${item.imageUrl}' />"
                                                 alt="${item.productName}" />

                                        </c:otherwise>

                                    </c:choose>

                                </div>

                                <!-- PRODUCT DETAILS -->

                                <div class="cart-details">

                                    <h2>
                                        ${item.productName}
                                    </h2>

                                    <p class="price">
                                        ₹ ${item.price}
                                    </p>

                                    <!-- QUANTITY CONTROLS -->

                                    <div class="qty-controls">

                                        <a href="<c:url value='/cart/update?cartItemId=${item.cartItemId}&action=decrease' />"
                                           class="qty-btn">

                                            -

                                        </a>

                                        <span class="qty-number">

                                            ${item.quantity}

                                        </span>

                                        <a href="<c:url value='/cart/update?cartItemId=${item.cartItemId}&action=increase' />"
                                           class="qty-btn">

                                            +

                                        </a>

                                    </div>

                                </div>

                                <!-- REMOVE BUTTON -->

                                <div class="cart-actions">

                                    <a href="<c:url value='/cart/remove?cartItemId=${item.cartItemId}' />"
                                       class="remove-btn">

                                        Remove

                                    </a>

                                </div>

                            </div>

                        </c:forEach>

                    </div>

                    <!-- ORDER SUMMARY -->

                    <div class="cart-summary">

                        <h2>
                            Order Summary
                        </h2>

                        <div class="summary-row">

                            <span>
                                Total Items
                            </span>

                            <span>
                                ${cartItems.size()}
                            </span>

                        </div>

                        <div class="summary-row total">

                            <span>
                                Total
                            </span>

                            <span>
                                ₹ ${totalAmount}
                            </span>

                        </div>

                        <a href="<c:url value='/checkout' />"
                           class="checkout-btn">

                            Proceed to Checkout

                        </a>

                    </div>

                </c:when>

               

                <c:otherwise>

                    <div class="empty-cart">

                        <h2>
                            Your cart is empty.
                        </h2>

                        <a href="<c:url value='/products' />"
                           class="shop-btn">

                            Shop Now

                        </a>

                    </div>

                </c:otherwise>

            </c:choose>

        </section>

    </main>

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>