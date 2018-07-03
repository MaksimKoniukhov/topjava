<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meal List</title>

    <style>
        table {
            border-collapse: collapse;
        }

        th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
<h2>Meal List</h2>

<c:choose>
    <c:when test="${!empty mealList}">
        <table style="border: 1px solid black;">
            <tr>
                <th width="120">Дата/Время</th>
                <th width="120">Описание</th>
                <th width="120">Калории</th>
            </tr>
            <c:forEach items="${mealList}" var="mealWithExceed">
                <c:choose>
                    <c:when test="${mealWithExceed.exceed}">
                        <tr style="color:red;">
                            <th><javatime:format value="${mealWithExceed.dateTime}" pattern="yyyy-MM-dd HH:mm"/></th>
                            <th>${mealWithExceed.description}</th>
                            <th>${mealWithExceed.calories}</th>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr style="color:green;">
                            <th><javatime:format value="${mealWithExceed.dateTime}" pattern="yyyy-MM-dd HH:mm"/></th>
                            <th>${mealWithExceed.description}</th>
                            <th>${mealWithExceed.calories}</th>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        No meal list found.
    </c:otherwise>
</c:choose>

</body>
</html>

