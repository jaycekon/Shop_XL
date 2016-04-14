<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/13 0013
  Time: 下午 5:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    body{
        height: 100%;
        text-align: center;
        line-height: 110;
    }
    html{
        height: 100%;
    }
</style>
<%
    String img =(String)request.getAttribute("code");
%>
<img src="<%=img%>" />
</body>
</html>
