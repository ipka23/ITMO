<%@ page import="utility.Point" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ru">
<head>
    <title>Таблица результатов</title>
    <link rel="stylesheet" href="css/resultTable.css">
</head>
<body>
<button data-intl_type="custom" data-intl_value="redirectButton" onclick="window.location.href='http://localhost:25230/lab2/'" type="button" id="redirectButton" class="redirectButton">Вернуться назад</button>
<div class="sessionPointsDiv">
    <table id="sessionPoints" border="1" class="sessionPoints">
        <thead>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th data-intl_type="custom" data-intl_value="hitMiss">Попадание/промах</th>
            <th data-intl_type="custom" data-intl_value="currentTime">Текущее время</th>
            <th data-intl_type="custom" data-intl_value="executionTime">Время выполнения</th>
        </tr>
        </thead>
        <tbody class="sessionPointsBody">
        <%
            LinkedHashSet<Point> points = (LinkedHashSet<Point>) request.getSession().getAttribute("points");
            if (points != null) {
                for (Point p : points) {
        %>
        <tr>
            <td><%=p.getX()%></td>
            <td><%=p.getY()%></td>
            <td><%=p.getR()%></td>
            <td><%=p.getStatus()%></td>
            <td><%=p.getCurrentTime()%></td>
            <td><%=p.getExecutionTime()%></td>
        </tr>
        <% }
        }%>
        </tbody>
    </table>
</div>
<script type="module">
    import {EasyIntl} from './js/libs/easy-intl/index.js'
    import dictionary from './js/intl/dictionary.js'
    import {updateHitMiss} from './js/script.js'
    const intl = new EasyIntl({
        autorun: false,
        dictionary
    })
    let lang = localStorage.getItem("lang")
    console.log(lang)
    if (lang === null) lang = "ru-RU"


    document.addEventListener("DOMContentLoaded", function () {
        intl.locale = lang
        intl.localize()
        updateHitMiss('sessionPoints')
    })
</script>
</body>
</html>


