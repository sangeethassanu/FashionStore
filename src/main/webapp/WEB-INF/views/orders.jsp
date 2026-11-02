<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<link rel="stylesheet"
      href="<c:url value='/assets/css/style.css' />">

<link rel="stylesheet"
      href="<c:url value='/assets/css/orders.css' />">

<div class="orders-page">

    <div class="container">

        <h1 class="orders-title">

            My Orders

        </h1>

        <c:choose>

            <c:when test="${not empty orders}">

                <div class="orders-list">

                    <c:forEach var="order"
                               items="${orders}">

                        <div class="order-card">

                            <h2>

                                Order #${order.orderId}

                            </h2>

                            <p>

                                <strong>
                                    Date:
                                </strong>

                                ${order.orderDate}

                            </p>

                            <p>

                                <strong>
                                    Status:
                                </strong>

                                ${order.orderStatus}

                            </p>

                            <p>

                                <strong>
                                    Payment:
                                </strong>

                                ${order.paymentMethod}

                            </p>

                            <p>

                                <strong>
                                    Total:
                                </strong>

                                ₹ ${order.totalAmount}

                            </p>

                        </div>

                    </c:forEach>

                </div>

            </c:when>

            <c:otherwise>

                <div class="empty-orders">

                    <p>

                        No orders available yet.

                    </p>

                </div>

            </c:otherwise>

        </c:choose>

    </div>

</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />