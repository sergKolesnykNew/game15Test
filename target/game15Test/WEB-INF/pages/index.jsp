<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<html>
<style>

    .tg {
        border-collapse: collapse;
        border-spacing: 0;
        border-color: #ccc;
    }

    .tg td {
        font-family: Arial, sans-serif;
        font-size: 14px;
        padding: 10px 5px;
        border-style: solid;
        border-width: 1px;
        overflow: hidden;
        word-break: normal;
        border-color: #ccc;
        color: #333;
        background-color: #fff;
    }

    .tg th {
        font-family: Arial, sans-serif;
        font-size: 14px;
        font-weight: normal;
        padding: 10px 5px;
        border-style: solid;
        border-width: 1px;
        overflow: hidden;
        word-break: normal;
        border-color: #b4cc8c;
        color: #333;
        background-color: darkturquoise;
    }

    .tg .tg-4eph {
        background-color: #f9f9f9
    }
</style>
<body>


<h2>Hello World!</h2>

<table class= "tg">
    <tr>
        <c:forEach var="f" items="${first}">
          <td>
                  ${f}
          </td>
        </c:forEach>
    </tr>
    <tr>
        <c:forEach var="s" items="${second}">
            <td>
                ${s}
            </td>
        </c:forEach>
    </tr>
    <tr>
        <c:forEach var="t" items="${third}">
            <td>
                ${t}
            </td>
        </c:forEach>
    </tr>
    <tr>
        <c:forEach var="f" items="${fourth}">
            <td>
                    ${f}
            </td>
        </c:forEach>
    </tr>


<tr>
    <td>
        <form action="/moveRight">
            <button>Right</button>
        </form>
    </td>
    <td>
<button>LEFT</button>
    </td>
    <td>
<button>UP</button>
    </td>
    <td>
<button>DOWN</button>
    </td>
</tr>

    <tr>
        <td>
            <button>EASY</button>
        </td>
        <td>
            <button>MIDDLE</button>
        </td>
        <td>
            <button>HARD</button>
        </td>
        <td>
            <button>BOT</button>
        </td>
    </tr>

</table>

</body>
</html>
