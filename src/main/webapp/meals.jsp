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
<h3><a href="index.html">Home</a></h3>

<h2>Моя Еда</h2>

<table style="border: 1px solid black;">
    <tr>
        <th width="120">Дата/Время</th>
        <th width="120">Описание</th>
        <th width="120">Калории</th>
        <th colspan="2">Редактировать/Удалить</th>
    </tr>
    <c:choose>
    <c:when test="${!empty mealList}">
    <c:forEach items="${mealList}" var="mealWithExceed">
        <c:choose>
            <c:when test="${mealWithExceed.exceed}">
                <tr style="color:red;">
                    <th><javatime:format value="${mealWithExceed.dateTime}" pattern="yyyy-MM-dd HH:mm"/></th>
                    <th>${mealWithExceed.description}</th>
                    <th>${mealWithExceed.calories}</th>
                    <th><a href="<c:url value='meals?action=edit&mealId=${mealWithExceed.id}'/>">Edit</a></th>
                    <th><a href="<c:url value='meals?action=delete&mealId=${mealWithExceed.id}'/>">Delete</a></th>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color:green;">
                    <th><javatime:format value="${mealWithExceed.dateTime}" pattern="yyyy-MM-dd HH:mm"/></th>
                    <th>${mealWithExceed.description}</th>
                    <th>${mealWithExceed.calories}</th>
                    <th><a
                            href="meals?action=edit&mealId=<c:out value="${mealWithExceed.id}"/>">Edit</a>
                    </th>
                    <th><a
                            href="meals?action=delete&mealId=<c:out value="${mealWithExceed.id}"/>">Delete</a>
                    </th>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</c:when>
<c:otherwise>
    <tr>
        <th colspan="4">No meal list found</th>
    </tr>
</c:otherwise>
</c:choose>
<h3>
    <a href="meals?action=insert">Добавить</a>
</h3>

</body>
</html>

