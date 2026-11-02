<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet"
      href="<c:url value='/assets/css/style.css' />">
<header class="navbar">

    <div class="container navbar-container">

        <!-- LOGO -->
        <div class="logo">

            <a href="<c:url value='/home' />">

                Fashion Store

            </a>

        </div>

        <!-- SEARCH BAR -->
        <form class="search-bar"
              action="<c:url value='/products' />"
              method="get">

            <input type="text"
                   name="keyword"
                   placeholder="Search for fashion products..." />

            <button type="submit">

                Search

            </button>

        </form>

        <!-- NAVIGATION -->
        <nav class="nav-links">

            <a href="<c:url value='/home' />">

                Home

            </a>

            <a href="<c:url value='/products' />">

                Products

            </a>

            <a href="<c:url value='/cart' />">

                Cart

            </a>

            <a href="<c:url value='/orders' />">

                Orders

            </a>

            <c:choose>

                <c:when test="${not empty sessionScope.loggedUser}">

                  <div class="nav-profile">


    <a href="<c:url value='/logout' />"
       class="logout-btn">

        Logout

    </a>

</div>
                </c:when>

                <c:otherwise>

                    <a href="<c:url value='/login' />">

                        Login

                    </a>

                    <a href="<c:url value='/register' />">

                        Register

                    </a>

                </c:otherwise>

            </c:choose>

        </nav>

    </div>

</header>