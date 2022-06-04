<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>
    <table border="1" cellpadding="8" cellspacing="0" align="center">
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Норма превышена?</th>
        </tr>
        <jsp:useBean id="mealList" type="java.util.List" scope="request"/>
        <c:forEach items="${mealList}" var="meal">
            <tr style="color:${meal.excess ? 'red' : 'green'}">
                <fmt:parseDate value="${meal.dateTime}" type="date" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
                <fmt:formatDate value="${parsedDate}" type="date" pattern="yyyy-MM-dd HH:mm" var="dateOfMeal"/>
                <td>${dateOfMeal}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${meal.excess ? 'да' : 'нет'}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>