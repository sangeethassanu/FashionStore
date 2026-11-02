<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>

<head>

    <title>Register - Fashion Store</title>

    <!-- MAIN CSS -->

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <!-- REGISTER CSS -->

    <link rel="stylesheet"
          href="<c:url value='/assets/css/register.css' />">

</head>

<body>

    <!-- NAVBAR -->

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <!-- REGISTER SECTION -->

    <section class="register-page">

        <div class="register-card">

            <h1>Create Your Account</h1>

            <p>
                Join Fashion Store and start shopping your favorite styles
            </p>

            <!-- ERROR MESSAGE -->

            <c:if test="${not empty error}">

                <div class="auth-error">

                    ${error}

                </div>

            </c:if>

            <!-- REGISTER FORM -->

            <form action="<c:url value='/register' />"
                  method="post"
                  class="register-form">

                <!-- FULL NAME -->

                <div class="form-group">

                    <label>Full Name</label>

                    <input type="text"
                           name="fullName"
                           placeholder="Enter your full name"
                           required />

                </div>

                <!-- EMAIL -->

                <div class="form-group">

                    <label>Email</label>

                    <input type="email"
                           name="email"
                           placeholder="Enter email"
                           required />

                </div>

                <!-- PHONE -->

                <div class="form-group">

                    <label>Phone</label>

                    <input type="text"
                           name="phone"
                           placeholder="Enter phone number" />

                </div>

                <!-- PASSWORD -->

                <div class="form-group">

                    <label>Password</label>

                    <input type="password"
                           name="password"
                           placeholder="Enter password"
                           required />

                </div>

                <!-- ADDRESS 1 -->

                <div class="form-group">

                    <label>Address Line 1</label>

                    <input type="text"
                           name="addressLine1"
                           placeholder="Address line 1" />

                </div>

                <!-- ADDRESS 2 -->

                <div class="form-group">

                    <label>Address Line 2</label>

                    <input type="text"
                           name="addressLine2"
                           placeholder="Address line 2" />

                </div>

                <!-- CITY -->

                <div class="form-group">

                    <label>City</label>

                    <input type="text"
                           name="city"
                           placeholder="City" />

                </div>

                <!-- STATE -->

                <div class="form-group">

                    <label>State</label>

                    <input type="text"
                           name="state"
                           placeholder="State" />

                </div>

                <!-- PINCODE -->

                <div class="form-group">

                    <label>Pincode</label>

                    <input type="text"
                           name="pincode"
                           placeholder="Pincode" />

                </div>

                <!-- COUNTRY -->

                <div class="form-group">

                    <label>Country</label>

                    <input type="text"
                           name="country"
                           placeholder="Country" />

                </div>

                <!-- SUBMIT -->

                <button type="submit"
                        class="register-btn">

                    Register

                </button>

            </form>

            <!-- LOGIN LINK -->

            <div class="auth-footer-text">

                Already have an account?

                <a href="<c:url value='/login' />">

                    Login

                </a>

            </div>

        </div>

    </section>

    <!-- FOOTER -->

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>