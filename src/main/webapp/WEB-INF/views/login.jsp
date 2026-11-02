<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>
        Login - Fashion Store
    </title>

    <!-- MAIN CSS -->
    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <!-- AUTH CSS -->
    <link rel="stylesheet"
          href="<c:url value='/assets/css/auth.css' />">

</head>

<body>

    <!-- NAVBAR -->
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <!-- LOGIN SECTION -->
    <section class="auth-page">

        <div class="auth-card">

            <!-- HEADING -->
            <h2>

                Welcome Back

            </h2>

            <!-- SUBTEXT -->
            <p class="auth-subtext">

                Login to continue shopping
                at Fashion Store

            </p>

            <!-- ERROR -->
            <c:if test="${not empty error}">

                <div class="auth-error">

                    ${error}

                </div>

            </c:if>

            <!-- LOGIN FORM -->
            <form action="<c:url value='/login' />"
                  method="post">

                <!-- EMAIL -->
                <div class="form-group">

                    <label>

                        Email

                    </label>

                    <input type="email"
                           name="email"
                           placeholder="Enter your email"
                           required>

                </div>

                <!-- PASSWORD -->
                <div class="form-group">

                    <label>

                        Password

                    </label>

                    <input type="password"
                           name="password"
                           placeholder="Enter your password"
                           required>

                </div>

                <!-- BUTTON -->
                <button type="submit"
                        class="auth-submit-btn">

                    Login

                </button>

            </form>

            <!-- FOOTER -->
            <div class="auth-footer-text">

                Don't have an account?

                <a href="<c:url value='/register' />">

                    Register here

                </a>

            </div>

        </div>

    </section>

    <!-- FOOTER -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>