<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>WebLab2</title>
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/styles.css">
    <script src="${pageContext.request.contextPath}/static/tablepage.js"></script>
</head>

<body>
<header>
    <!--шапка-->
    <h1>Зыков Андрей Алексеевич | P3206 | вариант 547291</h1>
</header>

<main>
    <div class="table-block">
        <table>
            <thead>
            <tr>
                <td>№</td>
                <td>X</td>
                <td>Y</td>
                <td>R</td>
                <td>Результат попадания</td>
                <td>Время обработки запроса, мс</td>
                <td>Текущее время</td>
            </tr>
            </thead>

            <tbody id="table-body">
            <c:if test="${not empty applicationScope.results}">
                <c:forEach var="res" items="${applicationScope.results}" varStatus="loop">
                    <tr class="data-row">
                        <td class="n">${loop.count}</td>
                        <td class="x">${res.x}</td>
                        <td class="y">${res.y}</td>
                        <td class="r">${res.r}</td>
                        <td class="result">${res.result ? 'Попадание' : 'Промах'}</td>
                        <td class="request-time">${res.requestTime}</td>
                        <td class="local-time">${res.localTime}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty applicationScope.results}">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
    <div>
        <button type="button" name="back" id="back-btn">Назад</button>
    </div>
</main>
</body>
</html>