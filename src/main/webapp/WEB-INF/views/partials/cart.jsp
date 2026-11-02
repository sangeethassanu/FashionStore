<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cart - Fashion Store</title>
    <link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
</head>

<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container">

    <h2>Your Cart</h2>

    <c:choose>

        <!-- CART ITEMS -->
        <c:when test="${not empty cartItems}">

            <c:forEach var="item" items="${cartItems}">

                <div class="cart-item">

                    <img src="<c:url value='/${item.imageUrl}' />"
                         width="100" />

                    <h3>${item.productName}</h3>
                    <p>Price: ₹ ${item.price}</p>
                    <p>Quantity: ${item.quantity}</p>

                </div>

                <hr/>

            </c:forEach>

        </c:when>

        <!-- EMPTY CART -->
        <c:otherwise>
            <p>Your cart is empty.</p>
        </c:otherwise>

    </c:choose>

</main>

</body>
</html>