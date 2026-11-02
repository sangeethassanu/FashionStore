<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>

<head>

    <title>
        Products - Fashion Store
    </title>

    <!-- MAIN CSS -->
    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <!-- PRODUCTS CSS -->
    <link rel="stylesheet"
          href="<c:url value='/assets/css/products.css' />">

</head>

<body>

    <!-- NAVBAR -->
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="products-page">

        <!-- HEADER -->
        <section class="products-header-card">

            <div class="container">

                <h1>

                    Explore Our Fashion Collection

                </h1>

                <p>

                    Browse products, apply filters,
                    and discover styles that match your taste

                </p>

            </div>

        </section>

        <section class="container products-layout">

            <!-- FILTER SECTION -->
            <aside class="filters-section">

                <h2>

                    Filters

                </h2>

                <form method="get"
                      action="<c:url value='/products' />">

                    <div class="filters-grid">

                        <!-- SEARCH -->
                        <div class="filter-group">

                            <label>

                                Search

                            </label>

                            <input type="text"
                                   name="keyword"
                                   placeholder="Search products..."
                                   value="${param.keyword}" />

                        </div>

                        <!-- CATEGORY -->
                        <div class="filter-group">

                            <label>

                                Category

                            </label>

                            <select name="categoryId">

                                <option value="">

                                    All Categories

                                </option>

                                <c:forEach var="category"
                                           items="${categories}">

                                    <option value="${category.categoryId}"

                                        <c:if test="${param.categoryId == category.categoryId}">

                                            selected

                                        </c:if>>

                                        ${category.categoryName}

                                    </option>

                                </c:forEach>

                            </select>

                        </div>

                        <!-- MIN PRICE -->
                        <div class="filter-group">

                            <label>

                                Min Price

                            </label>

                            <input type="number"
                                   name="minPrice"
                                   value="${param.minPrice}"
                                   placeholder="0" />

                        </div>

                        <!-- MAX PRICE -->
                        <div class="filter-group">

                            <label>

                                Max Price

                            </label>

                            <input type="number"
                                   name="maxPrice"
                                   value="${param.maxPrice}"
                                   placeholder="5000" />

                        </div>

                        <!-- SORT -->
                        <div class="filter-group">

                            <label>

                                Sort By

                            </label>

                            <select name="sort">

                                <option value="">

                                    Default

                                </option>

                                <option value="low">

                                    Price: Low → High

                                </option>

                                <option value="high">

                                    Price: High → Low

                                </option>

                            </select>

                        </div>

                    </div>

                    <!-- BUTTONS -->
                    <div class="filter-actions">

                        <button type="submit"
                                class="apply-btn">

                            Apply Filters

                        </button>

                        <a href="<c:url value='/products' />"
                           class="clear-btn">

                            Clear

                        </a>

                    </div>

                </form>

            </aside>

            <!-- PRODUCTS -->
            <div class="products-content">

                <div class="products-topbar">

                    <h2>

                        Products

                    </h2>

                    <p>

                        ${fn:length(products)} items found

                    </p>

                </div>

                <c:choose>

                    <%-- PRODUCTS AVAILABLE --%>

                    <c:when test="${not empty products}">

                        <div class="product-grid">

                            <c:forEach var="product"
                                       items="${products}">

                                <div class="product-card">

                                    <!-- IMAGE -->
                      <div class="product-image">

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

                                    <!-- INFO -->
                                    <div class="product-info">

                                        <h3>

                                            ${product.productName}

                                        </h3>

                                        <p class="product-brand">

                                            ${product.brand}

                                        </p>

                                        <p class="product-price">

                                            ₹ ${product.price}

                                        </p>

                                        <!-- PRODUCT URL -->
                                        <c:url var="productDetailsUrl"
                                               value="/product-details">

                                            <c:param name="productId"
                                                     value="${product.productId}" />

                                        </c:url>

                                        <!-- BUTTON -->
                                        <div class="product-actions">

                                            <a href="${productDetailsUrl}"
                                               class="btn-primary">

                                                View Details

                                            </a>

                                        </div>

                                    </div>

                                </div>

                            </c:forEach>

                        </div>

                    </c:when>

                    <%-- EMPTY --%>

                    <c:otherwise>

                        <div class="empty-state">

                            <p>

                                No products found.

                            </p>

                        </div>

                    </c:otherwise>

                </c:choose>

            </div>

        </section>

    </main>

    <!-- FOOTER -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>