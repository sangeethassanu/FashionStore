<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Order Success</title>

    <link rel="stylesheet"
          href="<c:url value='/assets/css/style.css' />">

</head>

<body>

    <jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

    <main style="
        min-height:70vh;
        display:flex;
        justify-content:center;
        align-items:center;
    ">

        <div style="
            background:#1e1e2f;
            padding:40px;
            border-radius:20px;
            text-align:center;
        ">

            <h1 style="margin-bottom:20px;">

                ✅ Order Placed Successfully

            </h1>

            <p style="margin-bottom:25px;">

                Thank you for shopping with us.

            </p>

            <a href="<c:url value='/products' />"
               class="btn-primary">

                Continue Shopping

            </a>

        </div>

    </main>

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>

</html>