<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit/Add Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <div align="center">
        <c:choose>
            <c:when test="${empty meal.id}">
                <h2>Добавить</h2>
            </c:when>
            <c:otherwise>
                <h2>Обновить</h2>
            </c:otherwise>
        </c:choose>
        <form method="POST" action="meals" enctype="application/x-www-form-urlencoded">
            <table>
                <tr>
                    <th>Атрибут</th>
                    <th>Значение</th>
                </tr>
                <c:if test="${not empty meal.id}">
                    <tr>
                        <td>ID</td>
                        <td><input readonly="readonly" name="id" value="${meal.id}"/></td>
                    </tr>
                </c:if>
                <tr>
                    <td>Дата/время</td>
                    <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}" required/></td>
                </tr>
                <tr>
                    <td>Описание</td>
                    <td><input type="text" name="description" value="${meal.description}"/></td>
                </tr>
                <tr>
                    <td>Калории</td>
                    <td><input type="number" name="calories" value="${meal.calories}" required/></td>
                </tr>
            </table>
            <button type="submit" id="submitButton">Сохранить</button>
            <button type="reset" onclick="window.history.back()">Отменить</button>
        </form>
    </div>
</section>
</body>
</html>