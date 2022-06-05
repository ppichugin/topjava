<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
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
<div align="center">
    <h2>Meals</h2>
    <p>Норма калорий в день: <%=MealsUtil.CALORIES_PER_DAY%></p>
</div>
<section>
    <table border="1" cellpadding="8" cellspacing="0" align="center">
        <tr align="right">
            <th colspan=5><a href="meals?action=add">Add Meal</a></th>
        </tr>
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th colspan=2>Action</th>
        </tr>
        <jsp:useBean id="mealList" type="java.util.List" scope="request"/>
        <c:forEach items="${mealList}" var="meal">
            <c:set value="${meal.id}" var="id"/>
            <tr style="color:${meal.excess ? 'red' : 'green'}">
                <fmt:parseDate value="${meal.dateTime}" type="date" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
                <fmt:formatDate value="${parsedDate}" type="date" pattern="dd-MM-yyyy HH:mm" var="dateOfMeal"/>
                <td>${dateOfMeal}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${id}">Update</a></td>
                <td><a href="meals?action=delete&id=${id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>