<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/5 0005
  Time: 下午 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- [[头部 -->
<header class="header container-fluid">
    <div class="row">
        <div class="col-xs-3">
            <img src="<%=request.getContextPath()%>/app/backStage/image/logo.jpg" alt="一内购" class="logo img-responsive">
            <button class="navbar-toggle collapsed" type="button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="col-xs-6 header_center">
            <h1 class="websiteName">百城万店倾销网平台</h1>
        </div>
        <div class="col-xs-3">
            <button class="btnExit fr" type="button" onclick="window.location.href='<%=request.getContextPath()%>/logOut'"><span class="glyphicon glyphicon-off"></span>退出</button>
        </div>
    </div>
</header><!-- 头部]] -->
