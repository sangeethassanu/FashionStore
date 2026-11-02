<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Fashion Store</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/home.css' />">

</head>

<body>

    <!-- NAVBAR -->

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <!-- HERO SECTION -->

    <section class="hero-section">

        <div class="container">

            <div class="hero-card">

                <!-- LEFT -->

                <div class="hero-content">

                    <span class="hero-badge">

                        New Season Collection

                    </span>

                    <h1>

                        Discover Your Style with
                        Fashion Store

                    </h1>

                    <p>

                        Explore the latest trends in fashion
                        for men, women, kids, and more.

                    </p>

                    <a href="<c:url value='/products' />"
                       class="btn-primary">

                        Shop Now

                    </a>

                </div>

                <!-- RIGHT -->

                <div class="hero-image">

                    <div class="hero-image-box">

                        <img src="<c:url value='/assets/images/fashion.jpg' />"
                             alt="Fashion">

                    </div>

                </div>

            </div>

        </div>

    </section>

    <!-- CATEGORIES -->

    <section class="categories-section">

        <div class="container">

            <h2 class="section-title">

                Shop by Category

            </h2>

            <div class="category-grid">

                <!-- MEN -->

                <div class="category-card men-card">

                    <div class="category-icon"></div>

                    <h3>Men</h3>

                    <p>Fashion products for men</p>

                    <a href="<c:url value='/products?category=Men' />"
                       class="btn-primary">

                        View Products

                    </a>

                </div>

                <!-- WOMEN -->

                <div class="category-card women-card">

                    <div class="category-icon"></div>

                    <h3>Women</h3>

                    <p>Fashion products for women</p>

                    <a href="<c:url value='/products?category=Women' />"
                       class="btn-primary">

                        View Products

                    </a>

                </div>

                <!-- KIDS -->

                <div class="category-card kids-card">

                    <div class="category-icon"></div>

                    <h3>Kids</h3>

                    <p>Fashion products for kids</p>

                    <a href="<c:url value='/products?category=Kids' />"
                       class="btn-primary">

                        View Products

                    </a>

                </div>

                <!-- ACCESSORIES -->

                <div class="category-card accessories-card">

                    <div class="category-icon"></div>

                    <h3>Accessories</h3>

                    <p>Fashion accessories</p>

                    <a href="<c:url value='/products?category=Accessories' />"
                       class="btn-primary">

                        View Products

                    </a>

                </div>

            </div>

        </div>

    </section>

    <!-- FOOTER -->

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>