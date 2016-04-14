<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/29 0029
  Time: 下午 3:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>一内购后台管理系统-登录页面</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/node_modules/bootstrap/dist/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/components.css">
    <style>
        html,body {
            height: 100%;
        }
        body {
            background: url(../image/bg1.jpg) right bottom no-repeat;
            background-color: #F0F9F8;
        }
    </style>
</head>
<body>

<div class="login_panel">
    <div class="login_header"><h1>一内购后台登录</h1></div>
    <div class="login_body">
        <form class="form-horizontal">
            <div id="loginTip"></div>
            <div class="input-group">
                <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                <input type="text" class="form-control" id="userName" placeholder="用户名">
            </div>
            <div class="input-group">
                <div class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></div>
                <input type="password" class="form-control" id="inputPassword" placeholder="密码" onkeydown="keyCheck()">
            </div>
            <div class="input-group">
                <button type="button" class="btn btn-info" id="login_btn">登录</button>
            </div>
        </form>

    </div>
</div>

<%--<script src="/node_modules/jquery/dist/jquery.min.js"></script>--%>
<%--<script src="/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>--%>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/components.js"></script>

</body>
</html>
