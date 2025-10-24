<%@ page import="utility.Point" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ru">
<script src="resultTable.js"></script>
<head>
    <title>Таблица результатов</title>
    <link rel="stylesheet" href="resultTable.css">
</head>
<body>
<button onclick="redirect()" type="button" id="redirectButton" class="redirectButton">Вернуться назад</button>
<div class="sessionPointsDiv">
    <table id="sessionPoints" border="1" class="sessionPoints">
        <thead>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Попадание/промах</th>
            <th>Текущее время</th>
            <th>Время выполнения</th>
        </tr>
        </thead>
        <tbody class="sessionPointsBody">
        <%
            LinkedList<Point> points = (LinkedList<Point>) request.getSession().getAttribute("points");
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
</body>
</html>


