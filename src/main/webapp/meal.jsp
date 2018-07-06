<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
    <title>Edit/Insert meal</title>
</head>
<body>
<h3><a href="meals">Назад</a></h3>

<c:choose>
    <c:when test="${meal.id != null}">
        <h2>Редактировать</h2>
    </c:when>
    <c:otherwise>
        <h2>Добавить</h2>
    </c:otherwise>
</c:choose>

<form action="meals" method="post">
    <fieldset>
        <div>
            <label hidden>Meal ID</label> <input hidden type="text"
                                                 name="mealId" value="<c:out value="${meal.id}" />"/>
        </div>
        <div>
            <label>Дата/Время</label> <input type="datetime-local"
                                             name="dateTime"
                                             value="<c:out value="${meal.dateTime}" />"/>
        </div>
        <div>
            <label>Описание</label> <input type="text"
                                           name="description"
                                           value="<c:out value="${meal.description}" />"
                                           placeholder="Описание"/>
        </div>
        <div>
            <label>Калории</label> <input type="text"
                                          name="calories"
                                          value="<c:out value="${meal.calories}" />"
                                          placeholder="Калории"/>
        </div>
        <div>
            <input type="submit" value="Submit"/>
        </div>
    </fieldset>
</form>
</body>
</html>
