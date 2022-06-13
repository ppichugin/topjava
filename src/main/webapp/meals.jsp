<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        dl {
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-grid;
            width: 100px;
        }

        dd {
            display: inline-block;
            margin-left: 5px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <form method="get" action="meals">
        <input type="hidden" name="action" id="action" value="filter">
        <dl>
            <dt>Start date:</dt>
            <dd><input type="date" value="${param.startDate}" id="startDate" name="startDate"></dd>
        </dl>
        <dl>
            <dt>End date:</dt>
            <dd><input type="date" value="${param.endDate}" id="endDate" name="endDate"></dd>
        </dl>
        <dl>
            <dt>Start time:</dt>
            <dd><input type="time" value="${param.startTime}" id="startTime" name="startTime"></dd>
        </dl>
        <dl>
            <dt>End time:</dt>
            <dd><input type="time" value="${param.endTime}" id="endTime" name="endTime"></dd>
        </dl>
        <button type="submit">Filter</button>
        <button onclick="window.location.href ='meals'" type="button">Cancel</button>
    </form>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>