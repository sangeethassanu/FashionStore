<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Checkout - Fashion Store</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

    <link rel="stylesheet"
          href="<c:url value='/assets/css/checkout.css' />">

</head>

<body>

    <!-- NAVBAR -->
    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main class="checkout-page">

        <div class="container">

            <div class="checkout-wrapper">

                <!-- LEFT -->
                <div class="checkout-form-card">

                    <h1>
                        Checkout
                    </h1>

                    <form action="<c:url value='/place-order' />"
                          method="post">

                        <!-- NAME -->
                        <div class="form-group">

                            <label>
                                Full Name
                            </label>

                            <input type="text"
                                   name="name"
                                   required />

                        </div>

                        <!-- EMAIL -->
                        <div class="form-group">

                            <label>
                                Email
                            </label>

                            <input type="email"
                                   name="email"
                                   required />

                        </div>

                        <!-- PHONE -->
                        <div class="form-group">

                            <label>
                                Phone Number
                            </label>

                            <input type="text"
                                   name="phone"
                                   required />

                        </div>

                        <!-- ADDRESS -->
                        <div class="form-group">

                            <label>
                                Delivery Address
                            </label>

                            <textarea name="address"
                                      rows="4"
                                      required></textarea>

                        </div>

                        <!-- PAYMENT -->
                        <div class="form-group">

                            <label>
                                Payment Method
                            </label>

                            <select name="paymentMethod">

                                <option value="COD">
                                    Cash on Delivery
                                </option>

                                <option value="UPI">
                                    UPI
                                </option>

                                <option value="CARD">
                                    Credit / Debit Card
                                </option>

                            </select>

                        </div>

                        <!-- BUTTON -->
                        <button type="submit"
                                class="place-order-btn">

                            Place Order

                        </button>

                    </form>

                </div>

                <!-- RIGHT -->
                <div class="checkout-summary">

                    <h2>
                        Order Summary
                    </h2>

                    <!-- PRODUCTS -->
                    <c:forEach var="item"
                               items="${cartItems}">

                        <div class="summary-row">

                            <span>
                                ${item.productName}
                            </span>

                            <span>
                                ₹ ${item.price}
                            </span>

                        </div>

                    </c:forEach>

                    <!-- TOTAL ITEMS -->
                    <div class="summary-row">

                        <span>
                            Total Items
                        </span>

                        <span>
                           ${totalItems}
                        </span>

                    </div>

                    <!-- TOTAL -->
                    <div class="summary-row total">

                        <span>
                            Total Amount
                        </span>

                        <span>
                            ₹ ${totalAmount}
                        </span>

                    </div>

                </div>

            </div>

        </div>

    </main>

    <!-- FOOTER -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>