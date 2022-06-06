<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit/Add Meal</title>
</head>
<script>
    window.addEventListener("load", function () {
        let datetimeField = document.getElementById("dateTime");
        if (datetimeField.value.length === 0) {
            let now = new Date();
            let offset = now.getTimezoneOffset() * 60000;
            let adjustedDate = new Date(now.getTime() - offset);
            datetimeField.value = adjustedDate.toISOString().substring(0, 16);
        }
    });
</script>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <div align="center">
        <h2>${empty meal.id ? 'Добавить' : 'Обновить'}</h2>
        <form method="POST" action="meals" enctype="application/x-www-form-urlencoded">
            <label><input readonly="readonly" name="id" value="${meal.id}" hidden/></label>
            <table>
                <tr>
                    <th>Атрибут</th>
                    <th>Значение</th>
                </tr>
                <tr>
                    <td>Дата/время</td>
                    <td><label for="dateTime"><input type="datetime-local" name="dateTime" id="dateTime"
                                                     value="${meal.dateTime}" required/></label></td>
                </tr>
                <tr>
                    <td>Описание</td>
                    <td><label><input type="text" name="description" value="${meal.description}"/></label></td>
                </tr>
                <tr>
                    <td>Калории</td>
                    <td><label><input type="number" name="calories" value="${meal.calories}" required/></label></td>
                </tr>
            </table>
            <button type="submit" id="submitButton">Сохранить</button>
            <button type="reset" onclick="window.history.back()">Отменить</button>
        </form>
    </div>
</section>
</body>
</html>