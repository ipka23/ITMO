<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>
    Веб лаба 2
  </title>
  <link rel="stylesheet" href="style.css">

</head>
<script src="index.js" defer></script>
<body>
<header class="header">
  Пчелкин Илья Игоревич, P3206, Вариант 467204
</header>
<table class="main">
  <tr>
    <td class="mainCell">
      <svg id="svg" width="300" height="300" viewBox="0 0 300 300">
        <polygon points="
            50,150
            150,50
            150,150
            " fill="#00BFFF" stroke="#00BFFF" stroke-width="1"/>
        <rect x="100" y="150" width="50" height="100" fill="#00BFFF" stroke="#00BFFF" stroke-width="1"/>
        <path d="M 250 150 A 100 100 0 0 1 150 250 L 150 150 L 250 150 Z" fill="#00BFFF" stroke="#00BFFF" stroke-width="1"/>
        <!--            lines-->
        <line x1="0" y1="150" x2="300" y2="150" stroke="black" stroke-width="1"/>
        <line x1="150" y1="0" x2="150" y2="300" stroke="black" stroke-width="1"/>
        <line x1="150" y1="0" x2="160" y2="10" stroke="black" stroke-width="1"/>
        <line x1="140" y1="10" x2="150" y2="0" stroke="black" stroke-width="1"/>
        <line x1="300" y1="150" x2="290" y2="160" stroke="black" stroke-width="1"/>
        <line x1="290" y1="140" x2="300" y2="150" stroke="black" stroke-width="1"/>
        <!--            Ox R-->
        <text x="50" y="145" fill="black">-R</text>
        <text x="100" y="145" fill="black">-R/2</text>
        <text x="200" y="145" fill="black">R/2</text>
        <text x="250" y="145" fill="black">R</text>
        <!--            Oy R-->
        <text x="155" y="50" fill="black">R</text>
        <text x="155" y="100" fill="black">R/2</text>
        <text x="160" y="200" fill="black">-R/2</text>
        <text x="160" y="250" fill="black">-R</text>
        <!--            axes names-->
        <text x="290" y="140" fill="black">x</text>
        <text x="160" y="10" fill="black">y</text>

        <!--            marks on lines-->
        <!--            Ox-->
        <line x1="50" y1="145" x2="50" y2="155" fill="black" stroke="black"/>
        <line x1="100" y1="145" x2="100" y2="155" fill="black" stroke="black"/>
        <line x1="200" y1="145" x2="200" y2="155" fill="black" stroke="black"/>
        <line x1="250" y1="145" x2="250" y2="155" fill="black" stroke="black"/>
        <!--            Oy-->
        <line x1="145" y1="50" x2="155" y2="50" fill="black" stroke="black"/>
        <line x1="145" y1="100" x2="155" y2="100" fill="black" stroke="black"/>
        <line x1="145" y1="200" x2="155" y2="200" fill="black" stroke="black"/>
        <line x1="145" y1="250" x2="155" y2="250" fill="black" stroke="black"/>
      </svg>
    </td>
    <td>
      <span>Введите X:</span>
      <form id="form" class="coordinates">
        <label><input type="radio" name="x" value="-2">-2</label>
        <label><input type="radio" name="x" value="-1.5">-1.5</label>
        <label><input type="radio" name="x" value="-1">-1</label>
        <label><input type="radio" name="x" value="-0.5">-0.5</label>
        <label><input type="radio" name="x" value="0">0</label>
        <label><input type="radio" name="x" value="0.5">0.5</label>
        <label><input type="radio" name="x" value="1">1</label>
        <label><input type="radio" name="x" value="1.5">1.5</label>
        <label><input type="radio" name="x" value="2">2</label>
        <br>
        <span id="xError" style="animation-duration: 2s"></span>
        <br>
        <span>Введите Y[-3;3]:</span>
        <label><input id="inputY" min="-3" max="3" type="number" step="any" maxlength="5"></label>
        <br>
        <span id="yError"></span>
        <br>
        <span>Введите R[2;5]:</span>
        <label><input id="inputR" min="2" max="5" type="number" step="any" maxlength="5"></label>
        <br>
        <span id="rError"></span>
        <br>
        <button id="submitButton" type="submit">Отправить</button>
      </form>
    </td>

  </tr>
  <tr>
    <td colspan="2">
      <div class="statsTableDiv">
        <table id="statsTable" border="1" class="statsTable">
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
          <tbody class="statsTableBody">
          <%= %>
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          <!--                        <tr> <td>1</td> <td>2</td> <td>3</td> <td>4</td> <td>5</td> <td>6</td> </tr>-->
          </tbody>
        </table>
      </div>
    </td>
  </tr>
</table>
</body>
</html>