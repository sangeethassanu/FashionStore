<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>

<head>

    <title>Product Details - Fashion Store</title>

    <!-- MAIN CSS -->
    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <!-- PRODUCT DETAILS CSS -->
    <link rel="stylesheet"
          href="<c:url value='/assets/css/product-details.css' />">

</head>

<body>

    <!-- NAVBAR -->
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="product-details-page">

        <section class="product-details-section">

            <div class="container">
               <div style="
    margin-bottom:25px;
    margin-top:10px;
">

    <a href="<c:url value='/products' />"
       style="
            background:#6c63ff;
            color:#ffffff;
            padding:14px 22px;
            border-radius:40px;
            text-decoration:none;
            font-size:16px;
            font-weight:bold;
            display:inline-block;
            box-shadow:0 4px 12px rgba(0,0,0,0.3);
       ">

        ← Back to Products

    </a>

</div>

                <!-- PRODUCT EXISTS -->
                <c:if test="${not empty product}">

                    <div class="product-details-card">

                        <!-- IMAGE -->
                        <div class="product-details-image">

                           <c:choose>

    <c:when test="${product.imageUrl.startsWith('http')}">

        <img src="${product.imageUrl}"
             alt="${product.productName}" />

    </c:when>

    <c:otherwise>

        <img src="<c:url value='/${product.imageUrl}' />"
             alt="${product.productName}" />

    </c:otherwise>

</c:choose>

                        </div>

                        <!-- PRODUCT INFO -->
                        <div class="product-details-info">

                            <h1>
                                ${product.productName}
                            </h1>

                            <p class="brand">
                                ${product.brand}
                            </p>

                            <p class="price">
                                ₹ ${product.price}
                            </p>

                            <p class="description">
                                ${product.description}
                            </p>

                            <!-- FORM -->
                            <form action="<c:url value='/cart/add' />"
                                  method="get">

                                <!-- PRODUCT ID -->
                                <input type="hidden"
                                       name="productId"
                                       value="${product.productId}" />

                                <!-- VARIANTS -->
                                <c:if test="${not empty variants}">

                                    <div class="variants">

                                        <h3>
                                            Available Sizes
                                        </h3>

                                        <c:forEach var="variant"
                                                   items="${variants}">

                                            <label class="variant-item">

                                                <input type="radio"
                                                       name="variantId"
                                                       value="${variant.variantId}"
                                                       required />

                                                <span>

                                                    Size:
                                                    ${variant.size}

                                                    <c:if test="${not empty variant.color}">

                                                        | Color:
                                                        ${variant.color}

                                                    </c:if>

                                                    (${variant.stockQuantity} left)

                                                </span>

                                            </label>

                                        </c:forEach>

                                    </div>

                                </c:if>

                                <!-- BUTTON -->
                                <div style="margin-top: 20px;">

                                    <button type="submit"
                                            class="btn-primary">

                                        Add to Cart

                                    </button>

                                </div>

                            </form>

                        </div>

                    </div>

                </c:if>

                <!-- PRODUCT NOT FOUND -->
                <c:if test="${empty product}">

                    <div class="empty-state">

                        <p>
                            Product not found.
                        </p>

                    </div>

                </c:if>

            </div>

        </section>

    </main>

    <!-- FOOTER -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>