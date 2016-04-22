<%@ page import="com.Shop.Model.Areas" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/13 0013
  Time: 下午 5:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="format-detection" content="telephone=no, email=no">
    <title>百城万店倾销网</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/lib/css/frozen.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/index.css">

</head>
<body>
<%
    Areas areas = (Areas)session.getAttribute("areas");
%>
<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>大区中心</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem active"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/areaCenter'"></i><div>大区中心</div></li>
    </ul>
</footer>

<section class="ui-container">

    <ul class="ui-list ui-list-text ui-border-b">
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>头像</h4>
            </div>
            <div class="ui-list-action">
                <img src="<%=areas.getImg()%>" alt="" class="picture">
            </div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>昵称</h4>
            </div>
            <div class="ui-list-action"><%=areas.getName()%></div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>ID</h4>
            </div>
            <div class="ui-list-action"><%=areas.getId()%></div>
        </li>
        <li class="ui-border-t">
            <div class="ui-list-info">
                <h4>我的推荐人</h4>
            </div>
            <div class="ui-list-action">平台</div>
        </li>
    </ul>

    <ul class="ui-list ui-list-text ui-list-link ui-border-tb  ui-list-active ui-list-cover gapT">
        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/roleList'">
            <h4>我的角色</h4>
        </li>
        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/withDraw'">
            <h4>我的佣金</h4>
        </li>

        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/roleCrod/<%=areas.getId()%>'">
           <h4>角色推广码</h4>
        </li>
        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/areaCommission'">
            <h4>分佣订单</h4>
        </li>
        <%--<li class="ui-border-t">--%>
            <%--<h4>联系信息</h4>--%>
        <%--</li>--%>
        <li class="ui-border-t" onclick="window.location.href='<%=request.getContextPath()%>/withdrawDetail'">
            <h4>提现明细</h4>
        </li>
    </ul>

</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>