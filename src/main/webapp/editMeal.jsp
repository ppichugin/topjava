<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit/Add Meal</title>
</head>
<script>
    window.addEventListener("load", function () {
        var now = new Date();
        var offset = now.getTimezoneOffset() * 60000;
        var adjustedDate = new Date(now.getTime() - offset);
        var formattedDate = adjustedDate.toISOString().substring(0, 16);
        var datetimeField = document.getElementById("myDatetimeField");
        if (datetimeField.value.length === 0) {
            datetimeField.value = formattedDate;
        }
    });
</script>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <div align="center">
        <h2><c:out value="${empty meal.id ? 'Добавить' : 'Обновить'}"/></h2>
        <form method="POST" action="meals" enctype="application/x-www-form-urlencoded">
            <table>
                <input readonly="readonly" name="id" value="${meal.id}" hidden/>
                <tr>
                    <th>Атрибут</th>
                    <th>Значение</th>
                </tr>
                <tr>
                    <td>Дата/время</td>
                    <td><input type="datetime-local" name="dateTime" id="myDatetimeField" value="${meal.dateTime}"
                               required/></td>
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